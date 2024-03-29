package theSorcerer;

import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.cardmods.InnateMod;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.actions.ChilledPowerApplyAction;
import theSorcerer.actions.ElementLoseAction;
import theSorcerer.actions.HeatedPowerApplyAction;
import theSorcerer.cards.DynamicCard;
import theSorcerer.modifiers.*;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.powers.DynamicPower;
import theSorcerer.powers.SelfRemovablePower;
import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.buff.HeatedPower;
import theSorcerer.powers.buff.PresenceOfMindPower;
import theSorcerer.powers.debuff.AblazePower;
import theSorcerer.powers.debuff.ElementlessPower;
import theSorcerer.powers.debuff.FrozenPower;
import theSorcerer.relics.DynamicRelic;
import theSorcerer.relics.ElementalMaster;
import theSorcerer.relics.ElementalPets;
import theSorcerer.relics.ProtectingGloves;
import theSorcerer.util.ElementAmount;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DynamicDungeon {

    private static final Logger LOG = LogManager.getLogger(DynamicDungeon.class.getName());

    private DynamicDungeon() {}


    // -------------------------
    // CARD
    // -------------------------

    public static boolean cardHasModifier(final AbstractCard card, final CardModifier modifier) {
        return CardModifierManager.hasModifier(card, modifier.cardMod.identifier(card));
    }

    public static boolean isFireCard(final AbstractCard card) {
        return isOriginalFireCard(card) || (isHeated() && isArcaneCard(card));
    }

    public static boolean isOriginalFireCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.FIRE);
    }

    public static boolean isIceCard(final AbstractCard card) {
        return isOriginalIceCard(card) || (isChilled() && isArcaneCard(card));
    }

    public static boolean isOriginalIceCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.ICE);
    }

    public static boolean isArcaneCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.ARCANE);
    }

    public static boolean canPlayArcane() {
        return hasRelic(ProtectingGloves.class) || DynamicDungeon.isHeatedOrChillder();
    }

    public static boolean isElementCostCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.ELEMENTCOST);
    }

    public static boolean isFuturityCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.FUTURITY);
    }

    public static boolean isFlashbackCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.FLASHBACK);
    }

    public static boolean isUnplayableCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.UNPLAYABLE);
    }

    public static boolean isEntombCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.ENTOMB);
    }

    public static boolean isEntombOrBottledGhostCard(final AbstractCard card) {
        return isEntombCard(card) || AbstractCardPatch.inBottleGhost.get(card);
    }

    public static boolean isInnateCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.INNATE);
    }

    public static boolean isAutoCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.AUTO);
    }

    public static boolean isEtherealCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.ETHEREAL);
    }

    public static boolean isRetainCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.RETAIN);
    }

    public static boolean isCopycatCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.COPYCAT) || cardHasModifier(card, CardModifier.COPYCAT_PLUS) ;
    }

    public static boolean isExhaustCard(final AbstractCard card) {
        return cardHasModifier(card, CardModifier.EXHAUST);
    }

    public static boolean isElementCard(final AbstractCard card) {
        return isFireCard(card) || isIceCard(card) || isArcaneCard(card);
    }

    public static void makeCardFuturity(final AbstractCard card) {
        if (!isFuturityCard(card)) {
            addModifierToCard(card, new FuturityMod());
        }
    }

    public static boolean canMakeCardFlashback(final AbstractCard card) {
        return !isFlashbackCard(card) && card.type != AbstractCard.CardType.STATUS && card.type != AbstractCard.CardType.CURSE;
    }

    public static void makeCardFlashback(final AbstractCard card) {
        if (!isFlashbackCard(card)) {
            addModifierToCard(card, new FlashbackMod());
        }
    }

    public static boolean canMakeCardFire(final AbstractCard card) {
        return !isFireCard(card) && !isArcaneCard(card) && card.type != AbstractCard.CardType.STATUS && card.type != AbstractCard.CardType.CURSE;
    }

    public static void makeCardFire(final AbstractCard card) {
        if (!isArcaneCard(card) && !isFireCard(card)) {
            if (card instanceof DynamicCard) {
                ((DynamicCard) card).triggerOnMakeFire();
            }
            addModifierToCard(card, new FireMod());
        }
    }

    public static boolean canMakeCardIce(final AbstractCard card) {
        return !isIceCard(card) && !isArcaneCard(card) && card.type != AbstractCard.CardType.STATUS && card.type != AbstractCard.CardType.CURSE;
    }

    public static void makeCardIce(final AbstractCard card) {
        if (!isArcaneCard(card) && !isIceCard(card)) {
            if (card instanceof DynamicCard) {
                ((DynamicCard) card).triggerOnMakeIce();
            }
            addModifierToCard(card, new IceMod());
        }
    }

    public static boolean canMakeCardArcane(final AbstractCard card) {
        return !isArcaneCard(card) && card.type != AbstractCard.CardType.STATUS && card.type != AbstractCard.CardType.CURSE;
    }

    public static void makeCardArcane(final AbstractCard card) {
        if (!isArcaneCard(card)) {
            if (card instanceof DynamicCard) {
                ((DynamicCard) card).triggerOnMakeArcane();
            }
            addModifierToCard(card, new ArcaneMod());
        }
    }

    public static boolean canMakeCardElementCost(final AbstractCard card) {
        return !isElementCostCard(card) && card.type != AbstractCard.CardType.STATUS && card.type != AbstractCard.CardType.CURSE;
    }

    public static void makeCardElementCost(final AbstractCard card) {
        if (!isElementCostCard(card)) {
            addModifierToCard(card, new ElementalCostMod());
        }
    }


    public static void makeCardEntomb(final AbstractCard card) {
        if (!isEntombCard(card)) {
            addModifierToCard(card, new EntombMod());
        }
    }

    public static void makeCardInnate(final AbstractCard card) {
        if (!isInnateCard(card)) {
            addModifierToCard(card, new InnateMod());
        }
    }

    public static void makeCardAuto(final AbstractCard card) {
        if (!isAutoCard(card)) {
            addModifierToCard(card, new AutoMod());
        }
    }

    public static void makeCardUnplayable(final AbstractCard card) {
        if (!isUnplayableCard(card)) {
            addModifierToCard(card, new UnplayableMod());
        }
    }

    public static void makeCardEthereal(final AbstractCard card) {
        if (!isEtherealCard(card)) {
            addModifierToCard(card, new EtherealMod());
        }
    }

    public static void makeCardRetain(final AbstractCard card) {
        if (!isRetainCard(card)) {
            addModifierToCard(card, new RetainMod());
        }
    }

    public static void makeCardExhaust(final AbstractCard card) {
        if (!isExhaustCard(card)) {
            addModifierToCard(card, new ExhaustMod());
        }
    }

    public static void addModifierToCard(
            final AbstractCard card,
            final CardModifier cardModifier
    ) {
        addModifierToCard(card, cardModifier.cardMod);
    }

    public static void addModifierToCard(
            final AbstractCard card,
            final AbstractCardModifier cardModifier
    ) {
        if (!CardModifierManager.hasModifier(card, cardModifier.identifier(card))) {
            CardModifierManager.addModifier(card, cardModifier);
        }
    }

    public static void removeModifierFromCard(
            final AbstractCard card,
            final CardModifier cardModifier
    ) {
        CardModifierManager.removeModifiersById(card, cardModifier.cardMod.identifier(card), true);
    }


    // -------------------------
    // DUNGEON
    // -------------------------

    public static void runIfNotArtifact(
            final AbstractCreature creature,
            final Runnable runnable
    ) {
        if (creature.hasPower(ArtifactPower.POWER_ID)) {
            creature.getPower(ArtifactPower.POWER_ID).onSpecificTrigger();
            return;
        }
        runnable.run();
    }

    public static void triggerRelic(final AbstractRelic relic) {
        relic.flash();
        addToBot(
                new RelicAboveCreatureAction(AbstractDungeon.player, relic)
        );
    }

    public static boolean applyElementless() {
        LOG.info("Trying to apply Elementless");
        AbstractPlayer player = AbstractDungeon.player;
        if (player.hasRelic(DynamicRelic.getID(ElementalMaster.class))) {
            LOG.info("Player has ElementMaster, cannot apply Elementless");
            AbstractRelic relic = player.getRelic(DynamicRelic.getID(ElementalMaster.class));
            triggerRelic(relic);
            return false;
        }
        else {
            loseElements();
            addToTop(
                    new ApplyPowerAction(
                            player,
                            player,
                            new ElementlessPower(player)
                    )
            );
            return true;
        }
    }

    public static void removeElementless() {
        withPowerDo(AbstractDungeon.player, ElementlessPower.class, SelfRemovablePower::removeSelf);
    }

    public static void runIfNotElementless(Runnable runnable) {
        if (hasElementless()) {
            flashElementlessRelic();
        }
        else {
            runnable.run();
        }
    }

    public static boolean hasElementless() {
        return hasPower(AbstractDungeon.player, ElementlessPower.class);
    }

    public static void flashElementlessRelic() {
        withPowerDo(AbstractDungeon.player, ElementlessPower.class, AbstractPower::flash);
    }

    public static void applyHeated(final int amount) {
        int realAmount = getAdaptedElementalAffinityAmount(amount);
        addToBot(
                new HeatedPowerApplyAction(
                        AbstractDungeon.player,
                        realAmount
                )
        );
    }

    public static void applyChilled(final int amount) {
        int realAmount = getAdaptedElementalAffinityAmount(amount);
        addToBot(
                new ChilledPowerApplyAction(
                        AbstractDungeon.player,
                        realAmount
                )
        );
    }

    private static int getAdaptedElementalAffinityAmount(final int amount) {
        int realAmount = amount;
        if (hasRelic(ElementalPets.class)) {
            realAmount += ElementalPets.AFFINITY_GAIN_PLUS;
            DynamicDungeon.triggerRelic(AbstractDungeon.player.getRelic(DynamicRelic.getID(ElementalPets.class)));
        }
        return realAmount;
    }

    public static void applyPresenceOfMind() {
        runIfNotElementless(() -> {
            if (!hasPresenceOfMind()) {
                addToBot(
                        new ApplyPowerAction(
                                AbstractDungeon.player,
                                AbstractDungeon.player,
                                new PresenceOfMindPower(AbstractDungeon.player)
                        )
                );
            }
        });
    }

    public static boolean hasPresenceOfMind() {
        return hasPower(AbstractDungeon.player, PresenceOfMindPower.class);
    }

    public static boolean isHeated() {
        return hasPower(AbstractDungeon.player, HeatedPower.class);
    }

    public static boolean isChilled() {
        return hasPower(AbstractDungeon.player, ChilledPower.class);
    }

    public static boolean isHeatedOrChillder() {
        return isHeated() || isChilled();
    }

    public static int getHeatedAmount() {
        return getElement(DynamicPower.getID(HeatedPower.class));
    }

    public static int getChilledAmount() {
        return getElement(DynamicPower.getID(ChilledPower.class));
    }

    public static void withAblazeDo(
            final AbstractMonster monster,
            Consumer<AblazePower> ablazePowerConsumer
    ) {
        withPowerDo(monster, AblazePower.class, ablazePowerConsumer);
    }

    public static void withFrozenDo(
            final AbstractMonster monster,
            Consumer<FrozenPower> frozenPowerConsumer
    ) {
        withPowerDo(monster, FrozenPower.class, frozenPowerConsumer);
    }


    private static int getElement(final String elementPowerId) {
        int amount = 0;
        AbstractPlayer player = AbstractDungeon.player;
        if (player.hasPower(elementPowerId)) {
            AbstractPower power = player.getPower(elementPowerId);
            amount = power.amount;
        }
        return amount;
    }

    public static ElementAmount getElementAmount() {
        return new ElementAmount(getHeatedAmount(), getChilledAmount());
    }

    public static void loseHeated() {
        loseHeated(getHeatedAmount());
    }


    public static void loseHeated(final int amount) {
        loseElements(DynamicPower.getID(HeatedPower.class), amount);
    }

    public static void loseChilled() {
        loseChilled(getChilledAmount());
    }

    public static void loseChilled(final int amount) {
        loseElements(DynamicPower.getID(ChilledPower.class), amount);
    }

    public static void loseElements() {
        if (isChilled()) {
            loseChilled();
        }
        else if (isHeated()) {
            loseHeated();
        }
    }

    public static void loseElements(final int amount) {
        if (isChilled()) {
            loseChilled(amount);
        }
        else if (isHeated()) {
            loseHeated(amount);
        }
    }

    private static void loseElements(final String elementPowerId, final int amount) {
        if (getElement(elementPowerId) > amount) {
            addToTop(
                    new ReducePowerAction(
                            AbstractDungeon.player,
                            AbstractDungeon.player,
                            elementPowerId,
                            amount
                    )
            );
        }
        else {
            addToTop(
                    new RemoveSpecificPowerAction(
                            AbstractDungeon.player,
                            AbstractDungeon.player,
                            elementPowerId
                    )
            );
        }
    }

    public static void loseAllElements() {
        addToTop(
                new ElementLoseAction(AbstractDungeon.player)
        );
    }

    public static void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public static void drawCard(final int amount) {
        addToBot(new DrawCardAction(amount));
    }

    public static void drawCard(final int amount, final AbstractGameAction followupAction) {
        addToBot(new DrawCardAction(amount, followupAction));
    }

    public static void gainEnergy(final int amount) {
        addToBot(new GainEnergyAction(amount));
    }

    public static void withAllMonstersDo(final Consumer<AbstractMonster> monsterConsumer) {
        AbstractDungeon.getCurrRoom().monsters.monsters
                .forEach(monsterConsumer);
    }

    public static CardGroup filterCardGroupBy(final CardGroup cardGroup, Predicate<AbstractCard> predicate) {
        final CardGroup newCardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        cardGroup.group.stream()
                .filter(predicate)
                .forEach(c -> newCardGroup.group.add(c));
        return newCardGroup;
    }

    public static Optional<AbstractCard> getLastCardPlayed() {
        ArrayList<AbstractCard> cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisCombat;
        return !cardsPlayed.isEmpty() ?
                Optional.of(cardsPlayed.get(cardsPlayed.size() - 1)) :
                Optional.empty();
    }

    public static boolean isLastCardPlayed(final Predicate<AbstractCard> predicate) {
        return getLastCardPlayed()
                .filter(predicate)
                .isPresent();
    }

    public static boolean hasPower(final AbstractCreature creature, final Class<? extends DynamicPower> thisClazz) {
        return creature.hasPower(DynamicPower.getID(thisClazz));
    }

    @SuppressWarnings("unchecked")
    public static <P extends DynamicPower> void withPowerDo(final AbstractCreature creature, final Class<P> thisClazz, Consumer<P> powerConsumer) {
        if (hasPower(creature, thisClazz)) {
            powerConsumer.accept((P) creature.getPower(DynamicPower.getID(thisClazz)));
        }
    }

    public static boolean hasAnyRelic(final Class<? extends DynamicRelic>... thisClazz) {
        return Arrays.stream(thisClazz).anyMatch(DynamicDungeon::hasRelic);
    }

    public static boolean hasRelic(final Class<? extends DynamicRelic> thisClazz) {
        return AbstractDungeon.player.hasRelic(DynamicRelic.getID(thisClazz));
    }

    @SuppressWarnings("unchecked")
    public static <P extends DynamicRelic> void withRelicDo(final Class<P> thisClazz, Consumer<P> relicConsumer) {
        if (hasRelic(thisClazz)) {
            relicConsumer.accept((P) AbstractDungeon.player.getRelic(DynamicRelic.getID(thisClazz)));
        }
    }

    public static void triggerOnPresenceOfMind() {
        triggerOnSpecific(
                DynamicCard::triggerOnPresenceOfMind,
                DynamicPower::triggerOnPresenceOfMind,
                DynamicRelic::triggerOnPresenceOfMind
        );
    }

    public static void triggerOnElementless() {
        triggerOnSpecific(
                DynamicCard::triggerOnElementless,
                DynamicPower::triggerOnElementless,
                DynamicRelic::triggerOnElementless
        );
    }

    public static void triggerOnNotElementlessAnymore() {
        triggerOnSpecific(
                DynamicCard::triggerOnNotElementlessAnymore,
                null,
                null
        );
    }

    public static void triggerOnFlashback(final AbstractCard card) {
        triggerOnSpecific(
                card,
                DynamicCard::triggerOnFlashback,
                DynamicPower::triggerOnFlashback,
                DynamicRelic::triggerOnFlashback
        );
    }

    public static void triggerOnFuturity(final AbstractCard card) {
        triggerOnSpecific(
                card,
                DynamicCard::triggerOnFuturity,
                DynamicPower::triggerOnFuturity,
                DynamicRelic::triggerOnFuturity
        );
    }

    public static void triggerOnSpecific(
            final Consumer<DynamicCard> cardConsumer,
            final Consumer<DynamicPower> powerConsumer,
            final Consumer<DynamicRelic> relicConsumer
    ) {
        triggerOnSpecific(
                null,
                cardConsumer,
                powerConsumer,
                relicConsumer
        );
    }

    public static void triggerOnSpecific(
            final AbstractCard card,
            final Consumer<DynamicCard> cardConsumer,
            final Consumer<DynamicPower> powerConsumer,
            final Consumer<DynamicRelic> relicConsumer
    ) {
        if (card == null && cardConsumer != null) {
            triggerCardsOnSpecific(AbstractDungeon.player.hand, cardConsumer);
            triggerCardsOnSpecific(AbstractDungeon.player.drawPile, cardConsumer);
            triggerCardsOnSpecific(AbstractDungeon.player.discardPile, cardConsumer);
            triggerCardsOnSpecific(AbstractDungeon.player.exhaustPile, cardConsumer);
        }
        if (card instanceof DynamicCard) {
            cardConsumer.accept((DynamicCard) card);
        }
        if (powerConsumer != null) {
            triggerPowersOnSpecific(powerConsumer);
        }
        if (relicConsumer != null) {
            triggerRelicsOnSpecific(relicConsumer);
        }
    }

    private static void triggerCardsOnSpecific(
            final CardGroup cardGroup,
            final Consumer<DynamicCard> consumer
    ) {
        cardGroup.group
                .stream()
                .filter(DynamicCard.class::isInstance)
                .map(DynamicCard.class::cast)
                .forEach(consumer);
    }

    private static void triggerPowersOnSpecific(
            final Consumer<DynamicPower> consumer
    ) {
        AbstractDungeon.player.powers.stream()
                .filter(DynamicPower.class::isInstance)
                .map(DynamicPower.class::cast)
                .forEach(consumer);
    }

    private static void triggerRelicsOnSpecific(
            final Consumer<DynamicRelic> consumer
    ) {
        AbstractDungeon.player.relics.stream()
                .filter(DynamicRelic.class::isInstance)
                .map(DynamicRelic.class::cast)
                .forEach(consumer);
    }

    public static PowerTip getPowerTip(final Class<? extends DynamicPower> powerClass) {
        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(DynamicPower.getID(powerClass));
        return getPowerTip(powerStrings.NAME);
    }

    public static PowerTip getPowerTip(final String keywordName) {
        return new PowerTip(
                TipHelper.capitalize(keywordName),
                GameDictionary.keywords.get(DynamicDungeon.makeKeywordID(keywordName))
        );
    }

    public static AbstractCard returnRandomFireCard() {
        return returnRandomCard(DynamicDungeon::isOriginalFireCard);
    }

    public static AbstractCard returnRandomIceCard() {
        return returnRandomCard(DynamicDungeon::isOriginalIceCard);
    }

    public static AbstractCard returnRandomCard(final Predicate<DynamicCard> predicate) {
        return allSorcererCards(predicate).getRandomCard(true);
    }

    public static CardGroup returnRandomCards(final int amount, final Predicate<DynamicCard> predicate) {
        final CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        allSorcererCards(predicate)
                .group
                .stream()
                .limit(amount)
                .forEach(cardGroup::addToRandomSpot);
        return cardGroup;
    }

    public static CardGroup allSorcererCards(final Predicate<DynamicCard> predicate) {
        final CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Set<AbstractCard> cards = new HashSet<>();
        cards.addAll(AbstractDungeon.srcCommonCardPool.group);
        cards.addAll(AbstractDungeon.srcUncommonCardPool.group);
        cards.addAll(AbstractDungeon.srcRareCardPool.group);
        cards
                .stream()
                .filter(DynamicCard.class::isInstance)
                .map(DynamicCard.class::cast)
                .filter(predicate)
                .forEach(cardGroup::addToRandomSpot);
        return cardGroup;
    }

    public static void modifyCardInDeck(final AbstractCard card) {
        int index = AbstractDungeon.player.masterDeck.group.indexOf(card);
        AbstractDungeon.player.masterDeck.group.set(index, card.makeStatEquivalentCopy());
    }

    public static CustomCard getCopyOfRandomCardInDeck(final Predicate<AbstractCard> predicate) {
        final CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractDungeon.player.masterDeck.group
                .stream()
                .filter(CustomCard.class::isInstance)
                .map(CustomCard.class::cast)
                .filter(predicate)
                .filter(c -> !DynamicDungeon.isCopycatCard(c))
                .forEach(cardGroup::addToRandomSpot);
        AbstractCard card = cardGroup.getRandomCard(true);
        return card != null ?
                (CustomCard) card.makeStatEquivalentCopy() :
                null;
    }


    // -------------------------
    // MOD
    // -------------------------

    public static String makeID(Class<?> thisClazz) {
        return makeID(thisClazz.getSimpleName());
    }

    public static String makeID(String idText) {
        return KirbyDeeMod.makeID(idText);
    }

    public static String makeKeywordID(String idText) {
        return KirbyDeeMod.makeKeywordID(idText);
    }
}

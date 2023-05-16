package theSorcerer;

import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.cardmods.InnateMod;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
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
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.actions.ElementLoseAction;
import theSorcerer.cards.DynamicCard;
import theSorcerer.modifiers.*;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;
import theSorcerer.powers.DynamicPower;
import theSorcerer.powers.SelfRemovablePower;
import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.buff.ElementPower;
import theSorcerer.powers.buff.HeatedPower;
import theSorcerer.powers.buff.PresenceOfMindPower;
import theSorcerer.powers.debuff.AblazePower;
import theSorcerer.powers.debuff.ElementlessPower;
import theSorcerer.powers.debuff.FrozenPower;
import theSorcerer.relics.DynamicRelic;
import theSorcerer.relics.ElementalMaster;
import theSorcerer.relics.ElementalPets;
import theSorcerer.relics.ProtectingGloves;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DynamicDungeon {

    private static final Logger LOG = LogManager.getLogger(DynamicDungeon.class.getName());

    private DynamicDungeon() {}


    // -------------------------
    // CARD
    // -------------------------

    public static boolean cardHasAbility(final AbstractCard card, final CardAbility ability) {
        return CardModifierManager.hasModifier(card, ability.cardMod.identifier(card));
    }

    public static boolean isFireCard(final AbstractCard card) {
        return isOriginalFireCard(card) || (isHeated() && isArcaneCard(card));
    }

    public static boolean isOriginalFireCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.FIRE);
    }

    public static boolean isIceCard(final AbstractCard card) {
        return isOriginalIceCard(card) || (isChilled() && isArcaneCard(card));
    }

    public static boolean isOriginalIceCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.ICE);
    }

    public static boolean isArcaneCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.ARCANE);
    }

    public static boolean canPlayArcane() {
        return hasRelic(ProtectingGloves.class) || DynamicDungeon.isHeatedOrChillder();
    }

    public static boolean isFuturityCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.FUTURITY);
    }

    public static boolean isFlashbackCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.FLASHBACK);
    }

    public static boolean isUnplayableCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.UNPLAYABLE);
    }

    public static boolean isEntombCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.ENTOMB);
    }

    public static boolean isEntombOrBottledGhostCard(final AbstractCard card) {
        return isEntombCard(card) || AbstractCardPatch.inBottleGhost.get(card);
    }

    public static boolean isInnateCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.INNATE);
    }

    public static boolean isAutoCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.AUTO);
    }

    public static boolean isEtherealCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.ETHEREAL);
    }

    public static boolean isRetainCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.RETAIN);
    }

    public static boolean isExhaustCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.EXHAUST);
    }

    public static boolean isElementCard(final AbstractCard card) {
        return isFireCard(card) || isIceCard(card) || isArcaneCard(card);
    }

    public static void makeCardFuturity(final AbstractCard card) {
        if (!isFuturityCard(card)) {
            makeCard(card, new FuturityMod());
        }
    }

    public static void makeCardFlashback(final AbstractCard card) {
        if (!isFlashbackCard(card)) {
            makeCard(card, new FlashbackMod());
        }
    }

    public static void makeCardFire(final AbstractCard card) {
        if (!isArcaneCard(card) && !isFireCard(card)) {
            makeCard(card, new FireMod());
        }
    }

    public static void makeCardIce(final AbstractCard card) {
        if (!isArcaneCard(card) && !isIceCard(card)) {
            makeCard(card, new IceMod());
        }
    }

    public static boolean canMakeCardArcane(final AbstractCard card) {
        return !isArcaneCard(card) && card.type != AbstractCard.CardType.STATUS && card.type != AbstractCard.CardType.CURSE;
    }

    public static void makeCardArcane(final AbstractCard card) {
        if (!isArcaneCard(card)) {
            makeCard(card, new ArcaneMod());
        }
    }

    public static void makeCardEntomb(final AbstractCard card) {
        if (!isEntombCard(card)) {
            makeCard(card, new EntombMod());
        }
    }

    public static void makeCardInnate(final AbstractCard card) {
        if (!isInnateCard(card)) {
            makeCard(card, new InnateMod());
        }
    }

    public static void makeCardAuto(final AbstractCard card) {
        if (!isAutoCard(card)) {
            makeCard(card, new AutoMod());
        }
    }

    public static void makeCardUnplayable(final AbstractCard card) {
        if (!isUnplayableCard(card)) {
            makeCard(card, new UnplayableMod());
        }
    }

    public static void makeCardEthereal(final AbstractCard card) {
        if (!isEtherealCard(card)) {
            makeCard(card, new EtherealMod());
        }
    }

    public static void makeCardRetain(final AbstractCard card) {
        if (!isRetainCard(card)) {
            makeCard(card, new RetainMod());
        }
    }

    public static void makeCardExhaust(final AbstractCard card) {
        if (!isExhaustCard(card)) {
            makeCard(card, new ExhaustMod());
        }
    }

    public static void makeCard(
            final AbstractCard card,
            final CardAbility cardAbility
    ) {
        makeCard(card, cardAbility.cardMod);
    }

    private static void makeCard(
            final AbstractCard card,
            final AbstractCardModifier cardModifier
    ) {
        CardModifierManager.addModifier(card, cardModifier);
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

    public static void applyElementless() {
        LOG.info("Trying to apply Elementless");
        AbstractPlayer player = AbstractDungeon.player;
        if (player.hasRelic(DynamicRelic.getID(ElementalMaster.class))) {
            LOG.info("Player has ElementMaster, cannot apply Elementless");
            AbstractRelic relic = player.getRelic(DynamicRelic.getID(ElementalMaster.class));
            triggerRelic(relic);
        }
        else {
            addToBot(
                    new ApplyPowerAction(
                            player,
                            player,
                            new ElementlessPower(player)
                    )
            );
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
        increaseElementPower(new HeatedPower(AbstractDungeon.player, realAmount), realAmount);
        addToTop(
                new VFXAction(
                        AbstractDungeon.player,
                        new InflameEffect(AbstractDungeon.player),
                        0.25F
                )
        );
    }

    public static void applyChilled(final int amount) {
        int realAmount = getAdaptedElementalAffinityAmount(amount);
        increaseElementPower(new ChilledPower(AbstractDungeon.player, realAmount), realAmount);
        // TODOO: frost effect
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

    public static void increaseElementPower(final ElementPower<?> elementPower, final int amount) {
        runIfNotElementless(() ->
                addToBot(
                        new ApplyPowerAction(
                                AbstractDungeon.player,
                                AbstractDungeon.player,
                                elementPower,
                                amount
                        )
                )
        );
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

    public static int getElementAmount() {
        int amount = DynamicDungeon.getHeatedAmount();
        if (amount <= 0) {
            amount = DynamicDungeon.getChilledAmount();
        }
        return amount;
    }

    public static void loseHeated(final int amount) {
        loseElements(DynamicPower.getID(HeatedPower.class), amount);
    }

    public static void loseChilled(final int amount) {
        loseElements(DynamicPower.getID(ChilledPower.class), amount);
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

    public static boolean hasPower(final AbstractCreature creature, final Class<? extends DynamicPower> thisClazz) {
        return creature.hasPower(DynamicPower.getID(thisClazz));
    }

    @SuppressWarnings("unchecked")
    public static <P extends DynamicPower> void withPowerDo(final AbstractCreature creature, final Class<P> thisClazz, Consumer<P> powerConsumer) {
        if (hasPower(creature, thisClazz)) {
            powerConsumer.accept((P) creature.getPower(DynamicPower.getID(thisClazz)));
        }
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

    public static void triggerOnElementless() {
        triggerOnSpecific(
                DynamicCard::triggerOnElementless,
                DynamicPower::triggerOnElementless,
                DynamicRelic::triggerOnElementless
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
        if (card == null) {
            triggerCardsOnSpecific(AbstractDungeon.player.hand, cardConsumer);
            triggerCardsOnSpecific(AbstractDungeon.player.drawPile, cardConsumer);
            triggerCardsOnSpecific(AbstractDungeon.player.discardPile, cardConsumer);
            triggerCardsOnSpecific(AbstractDungeon.player.exhaustPile, cardConsumer);
        }
        if (card instanceof DynamicCard) {
            cardConsumer.accept((DynamicCard) card);
        }
        triggerPowersOnSpecific(powerConsumer);
        triggerRelicsOnSpecific(relicConsumer);
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

    public static DynamicCard returnRandomFireCardInCombat() {
        return returnRandomElementCardInCombat(DynamicDungeon::isOriginalFireCard);
    }

    public static DynamicCard returnRandomIceCardInCombat() {
        return returnRandomElementCardInCombat(DynamicDungeon::isOriginalIceCard);
    }

    private static DynamicCard returnRandomElementCardInCombat(final Predicate<DynamicCard> elementPredicate) {
        Set<AbstractCard> cards = new HashSet<>();
        cards.addAll(AbstractDungeon.srcCommonCardPool.group);
        cards.addAll(AbstractDungeon.srcUncommonCardPool.group);
        cards.addAll(AbstractDungeon.srcRareCardPool.group);

        List<DynamicCard> elementCards = cards.stream()
                .filter(DynamicCard.class::isInstance)
                .map(DynamicCard.class::cast)
                .filter(elementPredicate)
                .collect(Collectors.toList());
        return elementCards.get(AbstractDungeon.cardRandomRng.random(elementCards.size() - 1));
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

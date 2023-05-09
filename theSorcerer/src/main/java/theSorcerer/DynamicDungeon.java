package theSorcerer;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
        return AbstractCardPatch.abilities.get(card).contains(ability);
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
        return DynamicDungeon.isEntombCard(card) || AbstractCardPatch.inBottleGhost.get(card);
    }

    public static boolean isAutoCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.AUTO);
    }

    public static boolean isElementCard(final AbstractCard card) {
        return isFireCard(card) || isIceCard(card) || isArcaneCard(card);
    }

    public static void makeCardFuturity(final AbstractCard card) {
        if (!isFuturityCard(card)) {
            AbstractCardPatch.abilities.get(card).add(CardAbility.FUTURITY);
            updateAbilityDescription(card);
        }
    }

    public static void makeCardFlashback(final AbstractCard card) {
        if (!isFlashbackCard(card)) {
            AbstractCardPatch.abilities.get(card).add(CardAbility.FLASHBACK);
            updateAbilityDescription(card);
        }
    }

    public static void makeCardFire(final AbstractCard card) {
        if (!isIceCard(card) && !isArcaneCard(card)) {
            AbstractCardPatch.abilities.get(card).add(CardAbility.FIRE);
            AbstractCardPatch.abilities.get(card).remove(CardAbility.ICE);
            updateAbilityDescription(card);
        }
    }

    public static void makeCardIce(final AbstractCard card) {
        if (!isIceCard(card) && !isArcaneCard(card)) {
            AbstractCardPatch.abilities.get(card).add(CardAbility.ICE);
            AbstractCardPatch.abilities.get(card).remove(CardAbility.FIRE);
            updateAbilityDescription(card);
        }
    }

    public static void makeCardArcane(final AbstractCard card) {
        if (!isArcaneCard(card)) {
            AbstractCardPatch.abilities.get(card).add(CardAbility.ARCANE);
            AbstractCardPatch.abilities.get(card).remove(CardAbility.FIRE);
            AbstractCardPatch.abilities.get(card).remove(CardAbility.ICE);
            updateAbilityDescription(card);
        }
    }

    public static void makeCardEntomb(final AbstractCard card) {
        if (!isEntombCard(card)) {
            AbstractCardPatch.abilities.get(card).add(CardAbility.ENTOMB);
            updateAbilityDescription(card);
        }
    }

    public static void updateAbilityDescription(final AbstractCard card) {
        // we have to re-initialize the description based on the abilities
        CardAbility.initializeAbilityRawDescriptions(card);

        // update shown description
        card.initializeDescription();
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
}

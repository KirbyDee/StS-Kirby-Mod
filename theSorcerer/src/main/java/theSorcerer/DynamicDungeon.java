package theSorcerer;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.actions.ElementLoseAction;
import theSorcerer.cards.DynamicCard;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;
import theSorcerer.powers.DynamicPower;
import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.buff.ElementPower;
import theSorcerer.powers.buff.HeatedPower;
import theSorcerer.powers.buff.PresenceOfMindPower;
import theSorcerer.powers.debuff.AblazePower;
import theSorcerer.powers.debuff.ElementlessPower;
import theSorcerer.powers.debuff.FrozenPower;
import theSorcerer.relics.ElementalMaster;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

public class DynamicDungeon {

    protected static final Logger LOG = LogManager.getLogger(DynamicDungeon.class.getName());

    private DynamicDungeon() {}


    // -------------------------
    // CARD
    // -------------------------

    public static boolean cardHasAbility(final AbstractCard card, final CardAbility ability) {
        return AbstractCardPatch.abilities.get(card).contains(ability);
    }

    public static boolean isFireCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.FIRE) || (isHeated() && isArcaneCard(card));
    }

    public static boolean isIceCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.ICE) || (isChilled() && isArcaneCard(card));
    }

    public static boolean isArcaneCard(final AbstractCard card) {
        return cardHasAbility(card, CardAbility.ARCANE);
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

    public static boolean isEntombOrBottledTombstoneCard(final AbstractCard card) {
        return DynamicDungeon.isEntombCard(card) || AbstractCardPatch.inBottleTombstone.get(card);
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
        if (!isFireCard(card) && !isArcaneCard(card)) {
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
        // in case of non-Dynamic Cards
        if (!(card instanceof DynamicCard)) {
            // we have to re-initialize the description based on the abilities
            CardAbility.initializeAbilityRawDescriptions(card);
        }

        // update shown description
        card.initializeDescription();
    }


    // -------------------------
    // DUNGEON
    // -------------------------

    public static void applyElementless() {
        LOG.info("Trying to apply Elementless");
        AbstractPlayer player = AbstractDungeon.player;
        if (player.hasRelic(ElementalMaster.ID)) {
            LOG.info("Player has ElementMaster, cannot apply Elementless");
            player.getRelic(ElementalMaster.ID).flash();
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
        if (hasElementless()) {
            ElementlessPower power = (ElementlessPower) AbstractDungeon.player.getPower(ElementlessPower.POWER_ID);
            power.removeSelf();
        }
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
        return AbstractDungeon.player.hasPower(ElementlessPower.POWER_ID);
    }

    public static void flashElementlessRelic() {
        AbstractDungeon.player.getPower(ElementlessPower.POWER_ID).flash();
    }

    public static void applyHeated(final int amount) {
        increaseElementPower(new HeatedPower(AbstractDungeon.player, amount), amount);
    }

    public static void applyChilled(final int amount) {
        increaseElementPower(new ChilledPower(AbstractDungeon.player, amount), amount);
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
        return AbstractDungeon.player.hasPower(PresenceOfMindPower.POWER_ID);
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
        return getHeatedAmount() > 0;
    }

    public static boolean isChilled() {
        return getChilledAmount() > 0;
    }

    public static boolean isHeatedOrChillder() {
        return isHeated() || isChilled();
    }

    public static int getHeatedAmount() {
        return getElement(HeatedPower.POWER_ID);
    }

    public static int getChilledAmount() {
        return getElement(ChilledPower.POWER_ID);
    }

    public static void withAblaze(
            final AbstractMonster monster,
            Consumer<AblazePower> ablazePowerConsumer
    ) {
        if (monster.hasPower(AblazePower.POWER_ID)) {
            ablazePowerConsumer.accept((AblazePower) monster.getPower(AblazePower.POWER_ID));
        }
    }

    public static void withFrozen(
            final AbstractMonster monster,
            Consumer<FrozenPower> frozenPowerConsumer
    ) {
        if (monster.hasPower(FrozenPower.POWER_ID)) {
            frozenPowerConsumer.accept((FrozenPower) monster.getPower(FrozenPower.POWER_ID));
        }
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

    public static void gainEnergy(final int amount) {
        addToBot(new GainEnergyAction(amount));
    }

    public static void withAllMonsters(final Consumer<AbstractMonster> monsterConsumer) {
        AbstractDungeon.getCurrRoom().monsters.monsters
                .forEach(monsterConsumer);
    }

    public static Optional<AbstractCard> getLastCardPlayed() {
        ArrayList<AbstractCard> cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisCombat;
        return !cardsPlayed.isEmpty() ?
                Optional.of(cardsPlayed.get(cardsPlayed.size() - 1)) :
                Optional.empty();
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
}

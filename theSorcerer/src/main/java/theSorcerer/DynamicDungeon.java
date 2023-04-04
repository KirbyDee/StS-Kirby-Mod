package theSorcerer;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.actions.ElementLoseAction;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.buff.ElementPower;
import theSorcerer.powers.buff.HeatedPower;
import theSorcerer.powers.debuff.ElementlessPower;
import theSorcerer.relics.ElementalMaster;

import java.util.function.Consumer;

public class DynamicDungeon {

    protected static final Logger LOG = LogManager.getLogger(DynamicDungeon.class.getName());

    private DynamicDungeon() {}

    public static boolean isFireCard(final AbstractCard card) {
        return cardHasTag(card, SorcererCardTags.FIRE);
    }

    public static boolean isIceCard(final AbstractCard card) {
        return cardHasTag(card, SorcererCardTags.ICE);
    }

    public static boolean isFuturityCard(final AbstractCard card) {
        return cardHasTag(card, SorcererCardTags.FUTURITY);
    }

    public static boolean isFlashbackCard(final AbstractCard card) {
        return cardHasTag(card, SorcererCardTags.FLASHBACK);
    }

    public static boolean isElementCard(final AbstractCard card) {
        return isFireCard(card) || isIceCard(card);
    }

    public static boolean cardHasTag(final AbstractCard card, final AbstractCard.CardTags tag) {
        return card.hasTag(tag);
    }

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

    public static void increaseElementPower(final ElementPower<?> elementPower, final int amount) {
        addToBot(
                new ApplyPowerAction(
                        AbstractDungeon.player,
                        AbstractDungeon.player,
                        elementPower,
                        amount
                )
        );
    }

    public static boolean isHeated() {
        return getHeatedAmount() > 0;
    }

    public static boolean isChilled() {
        return getChilledAmount() > 0;
    }

    public static int getHeatedAmount() {
        return getElement(HeatedPower.POWER_ID);
    }

    public static void withHeatedAmount(Consumer<Integer> amountConsumer) {
        amountConsumer.accept(getHeatedAmount());
    }

    public static int getChilledAmount() {
        return getElement(ChilledPower.POWER_ID);
    }

    public static void withChilledAmount(Consumer<Integer> amountConsumer) {
        amountConsumer.accept(getChilledAmount());
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

}

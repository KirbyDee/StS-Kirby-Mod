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
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;
import theSorcerer.powers.buff.FireAffinityPower;
import theSorcerer.powers.buff.IceAffinityPower;
import theSorcerer.powers.debuff.ElementlessPower;
import theSorcerer.relics.ElementalMaster;

import java.util.function.Consumer;

public class DynamicDungeon {

    protected static final Logger LOG = LogManager.getLogger(DynamicDungeon.class.getName());

    private DynamicDungeon() {}

    public static boolean isFireCard(final AbstractCard card) {
        return isElementCard(card, CardAbility.FIRE);
    }

    public static boolean isIceCard(final AbstractCard card) {
        return isElementCard(card, CardAbility.ICE);
    }

    public static boolean isElementCard(final AbstractCard card) {
        return isFireCard(card) || isIceCard(card);
    }

    public static boolean isElementCard(final AbstractCard card, final CardAbility cardAbility) {
        return AbstractCardPatch.abilities.get(card).contains(cardAbility);
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

    public static boolean hasElementless() {
        return AbstractDungeon.player.hasPower(ElementlessPower.POWER_ID);
    }

    public static int getFireAffinityAmount() {
        return getAffinity(FireAffinityPower.POWER_ID);
    }

    public static void withFireAffinityAmount(Consumer<Integer> amountConsumer) {
        amountConsumer.accept(getFireAffinityAmount());
    }

    public static int getIceAffinityAmount() {
        return getAffinity(IceAffinityPower.POWER_ID);
    }

    public static void withIceAffinityAmount(Consumer<Integer> amountConsumer) {
        amountConsumer.accept(getIceAffinityAmount());
    }

    private static int getAffinity(final String affinityPowerId) {
        int amount = 0;
        AbstractPlayer player = AbstractDungeon.player;
        if (player.hasPower(affinityPowerId)) {
            AbstractPower power = player.getPower(affinityPowerId);
            amount = power.amount;
        }
        return amount;
    }

    public static int getElementAffinityAmount() {
        int amount = DynamicDungeon.getFireAffinityAmount();
        if (amount <= 0) {
            amount = DynamicDungeon.getIceAffinityAmount();
        }
        return amount;
    }

    public static void loseAllElements() {
        addToBot(
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

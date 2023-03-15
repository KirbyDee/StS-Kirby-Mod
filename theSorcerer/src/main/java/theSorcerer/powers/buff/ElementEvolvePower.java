package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.powers.SelfRemovablePower;

public abstract class ElementEvolvePower<E extends AbstractPower> extends SelfRemovablePower {

    private static final Logger LOG = LogManager.getLogger(ElementEvolvePower.class.getName());

    private final String thisElementID;

    protected int affinityAmount;

    public ElementEvolvePower(
            final AbstractCreature owner,
            final int affinityAmount,
            final String thisEvolvedPowerID,
            final String thisElementID
    ) {
        super(owner, thisEvolvedPowerID);
        this.thisElementID = thisElementID;
        this.affinityAmount = affinityAmount;
        this.isTurnBased = true;
        this.canGoNegative = false;

        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        applyExtraPower(this.affinityAmount);
    }

    private void applyExtraPower(final int amount) {
        if (amount > 0) {
            LOG.info("Apply Extra Power: " + amount);
            addToBot(
                    new ApplyPowerAction(
                            this.owner,
                            this.owner,
                            createExtraPower(amount)
                    )
            );
        }
    }

    protected abstract E createExtraPower(final int amount);

    @Override
    public void atEndOfRound() {
        removeSelf();
    }

    @Override
    public void removeSelf() {
        reduceExtraPower();
        super.removeSelf();
    }

    private void reduceExtraPower() {
        if (this.affinityAmount <= 0) {
            return;
        }

        LOG.info("Reduce Extra Power by  " + this.affinityAmount);
        addToBot(
                new ReducePowerAction(
                        this.owner,
                        this.owner,
                        getExtraPowerId(),
                        this.affinityAmount
                )
        );
    }

    protected abstract String getExtraPowerId();

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        AbstractCard.CardTags tag = getAffinityLoseTag();
        if (card.tags.contains(tag)) {
            LOG.info(tag + " applied, but " + this.ID + " already existing -> remove " + this.ID);
            removeSelf();
            reduceExtraPower();
        }
    }

    protected abstract AbstractCard.CardTags getAffinityLoseTag();

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(this.thisElementID)) {
            int stackAmount = power.amount;
            this.affinityAmount += stackAmount;
            LOG.info(this.thisElementID + " applied, and " + this.ID + " already existing -> apply extra power: " + stackAmount);
            applyExtraPower(stackAmount);
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        description = this.descriptions[0] + affinityAmount + this.descriptions[1] + affinityAmount + this.descriptions[2];
    }
}

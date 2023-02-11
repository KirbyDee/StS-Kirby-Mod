package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.powers.SelfRemovablePower;

public abstract class ElementAffinityPower<E extends ElementEvolvePower<?>> extends SelfRemovablePower {

    private static final Logger LOG = LogManager.getLogger(ElementAffinityPower.class.getName());

    private final String otherElementID;

    private boolean hasEvolved;

    public ElementAffinityPower(
            final AbstractCreature owner,
            final int amount,
            final String thisElementID,
            final String otherElementID
    ) {
        super(owner, thisElementID);
        this.otherElementID = otherElementID;
        this.type = PowerType.BUFF;
        this.hasEvolved = false;
        this.isTurnBased = true;
        this.canGoNegative = false;

        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }

        updateDescription();
    }

    @Override
    public void atEndOfRound() {
        flash();
        reducePowerToZero();
    }

    protected void reducePowerToZero() {
        LOG.info("Reduce " + this.ID + " to zero");
        addToBot(
                new ReducePowerAction(
                        this.owner,
                        this.owner,
                        this.ID,
                        this.amount
                )
        );
    }

    @Override
    public void updateDescription() {
        description = this.descriptions[0] + "3" + this.descriptions[1]; // TODO
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(otherElementID)) {
            LOG.info(this.otherElementID + " applied, but " + this.ID + " already existing -> remove " + this.ID);
            reducePowerToZero();
            removeSelf();
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        stackAmount(stackAmount);
        checkForEvolve();
    }

    private void stackAmount(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            removeSelf();
        }

        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    private void checkForEvolve() {
        LOG.info(this.ID + " amount: " + this.amount);
        if (!this.hasEvolved && this.amount >= 3) { // TODO
            applyElementEvolve();
        }
    }

    private void applyElementEvolve() {
        LOG.info("Apply evolve power");
        this.hasEvolved = true;
        addToBot(
                new ApplyPowerAction(
                        this.owner,
                        this.owner,
                        createEvolvePower()
                )
        );
    }

    protected abstract E createEvolvePower();
}

package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.powers.DynamicAmountPower;

public abstract class ElementPower<E extends AbstractPower> extends DynamicAmountPower {

    private static final Logger LOG = LogManager.getLogger(ElementPower.class.getName());

    public ElementPower(
            Class<? extends ElementPower<E>> thisClazz,
            final AbstractCreature owner,
            final int amount
    ) {
        super(thisClazz, owner, amount, false);
        this.isTurnBased = true;
        this.canGoNegative = false;

        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        applyExtraPower(stackAmount);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        applyExtraPower(this.amount);
    }

    private void applyExtraPower(final int amount) {
        if (amount > 0) {
            LOG.info("Apply Extra Power: " + amount);
            addToTop(
                    new ApplyPowerAction(
                            this.owner,
                            this.owner,
                            createExtraPower(amount)
                    )
            );
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        reduceExtraPower(reduceAmount);
        updateDescription();
    }

    @Override
    public void onRemove() {
        super.onRemove();
        reducePowerToZero();
    }

    protected abstract E createExtraPower(final int amount);

    @Override
    public void atEndOfRound() {
        removeSelf();
    }

    public void reducePowerToZero() {
        reducePower(this.ID, this.amount);
        reduceExtraPower(this.amount);
    }

    private void reducePower(final String powerId, final int amount) {
        if (amount <= 0) {
            return;
        }
        LOG.info("Reduce " + powerId + " by  " + amount);

        addToTop(
                new ReducePowerAction(
                        this.owner,
                        this.owner,
                        powerId,
                        amount
                )
        );
    }

    private void reduceExtraPower(final int amount) {
        if (amount <= 0) {
            return;
        }

        addToTop(
                new ApplyPowerAction(
                        this.owner,
                        this.owner,
                        createExtraPower(-amount),
                        -amount)
        );
    }

    @Override
    public void updateDescription() {
        this.description = this.descriptions[0] + this.amount + this.descriptions[1] + this.amount + this.descriptions[2];
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.BUFF;
    }
}

package theSorcerer.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DynamicAmountPower extends SelfRemovablePower {

    private static final Logger LOG = LogManager.getLogger(DynamicAmountPower.class.getName());

    public DynamicAmountPower(
            Class<? extends DynamicAmountPower> thisClazz,
            AbstractCreature owner,
            int amount
    ) {
        super(thisClazz, owner);

        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        LOG.info("Stack power " + this.ID + " by " + stackAmount);
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            removeSelf();
        }

        if (this.amount > 999) {
            this.amount = 999;
        }

        updateDescription();
    }
}

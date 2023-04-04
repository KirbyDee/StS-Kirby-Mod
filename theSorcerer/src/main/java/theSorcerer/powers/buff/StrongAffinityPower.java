package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.core.AbstractCreature;
import theSorcerer.powers.SelfRemovablePower;

public abstract class StrongAffinityPower extends SelfRemovablePower {

    public StrongAffinityPower(
            AbstractCreature owner,
            int amount,
            String powerId
    ) {
        super(owner, powerId);
        this.amount = amount;

        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    @Override
    public void atStartOfTurn() {
        if (this.amount > 0) {
            flash();
            applyElementPower(this.amount);
        }
        removeSelf();
    }

    protected abstract void applyElementPower(final int amount);

    @Override
    public void updateDescription() {
        this.description = descriptions[0] + this.amount + descriptions[1];
    }
}

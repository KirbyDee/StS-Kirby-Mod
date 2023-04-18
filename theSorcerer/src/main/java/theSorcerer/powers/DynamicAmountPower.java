package theSorcerer.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

public abstract class DynamicAmountPower extends SelfRemovablePower {

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

    public DynamicAmountPower(
            final AbstractCreature owner,
            String powerID,
            int amount
    ) {
        super(owner, powerID);

        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    @Override
    public void stackPower(int stackAmount) {
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

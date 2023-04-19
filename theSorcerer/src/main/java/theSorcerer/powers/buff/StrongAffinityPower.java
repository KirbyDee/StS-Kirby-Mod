package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.core.AbstractCreature;
import theSorcerer.powers.DynamicAmountPower;

public abstract class StrongAffinityPower extends DynamicAmountPower {

    public StrongAffinityPower(
            Class<? extends StrongAffinityPower> thisClazz,
            AbstractCreature owner,
            int amount
    ) {
        super(
                thisClazz,
                owner,
                amount
        );

        updateDescription();
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

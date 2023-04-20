package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.DynamicDungeon;

public class StrongFireAffinityPower extends StrongAffinityPower {

    public StrongFireAffinityPower(
            AbstractCreature owner,
            int amount
    ) {
        super(
                StrongFireAffinityPower.class,
                owner,
                amount
        );
    }

    @Override
    public AbstractPower makeCopy() {
        return new StrongFireAffinityPower(this.owner, this.amount);
    }

    @Override
    protected void applyElementPower(final int amount) {
        DynamicDungeon.applyHeated(amount);
    }
}

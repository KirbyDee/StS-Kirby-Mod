package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.DynamicDungeon;

// TODO: new image
public class StrongIceAffinityPower extends StrongAffinityPower {

    public StrongIceAffinityPower(
            AbstractCreature owner,
            int amount
    ) {
        super(
                StrongIceAffinityPower.class,
                owner,
                amount
        );
    }

    @Override
    public AbstractPower makeCopy() {
        return new StrongIceAffinityPower(this.owner, this.amount);
    }

    @Override
    protected void applyElementPower(final int amount) {
        DynamicDungeon.applyChilled(amount);
    }
}

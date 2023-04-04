package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.DynamicDungeon;
import theSorcerer.KirbyDeeMod;

public class StrongFireAffinityPower extends StrongAffinityPower {

    public static final String POWER_NAME = "StrongFireAffinityPower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public StrongFireAffinityPower(
            AbstractCreature owner,
            int amount
    ) {
        super(owner, amount, POWER_ID);
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

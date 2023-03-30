package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
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
    protected ElementAffinityPower<?> createAffinityPower() {
        return new FireAffinityPower(this.owner, 1);
    }

    @Override
    public AbstractPower makeCopy() {
        return new StrongFireAffinityPower(this.owner, this.amount);
    }
}

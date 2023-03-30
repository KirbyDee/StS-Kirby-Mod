package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.KirbyDeeMod;

public class StrongIceAffinityPower extends StrongAffinityPower {

    public static final String POWER_NAME = "StrongIceAffinityPower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public StrongIceAffinityPower(
            AbstractCreature owner,
            int amount
    ) {
        super(owner, amount, POWER_ID);
    }

    @Override
    protected ElementAffinityPower<?> createAffinityPower() {
        return new IceAffinityPower(this.owner, 1);
    }

    @Override
    public AbstractPower makeCopy() {
        return new StrongIceAffinityPower(this.owner, this.amount);
    }
}

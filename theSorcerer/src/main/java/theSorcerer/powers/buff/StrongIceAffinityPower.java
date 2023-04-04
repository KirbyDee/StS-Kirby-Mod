package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.DynamicDungeon;
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
    public AbstractPower makeCopy() {
        return new StrongIceAffinityPower(this.owner, this.amount);
    }

    @Override
    protected void applyElementPower(final int amount) {
        DynamicDungeon.applyChilled(amount);
    }
}

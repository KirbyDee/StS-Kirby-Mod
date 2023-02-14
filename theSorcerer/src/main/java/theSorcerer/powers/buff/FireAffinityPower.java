package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.KirbyDeeMod;

public class FireAffinityPower extends ElementAffinityPower<HeatedPower> {

    public static final String POWER_NAME = "FireAffinityPower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public FireAffinityPower(
            final AbstractCreature owner,
            final int amount
    ) {
        super(
                owner,
                amount,
                POWER_ID,
                IceAffinityPower.POWER_ID
        );
    }

    @Override
    public AbstractPower makeCopy() {
        return new FireAffinityPower(owner, amount);
    }

    @Override
    protected HeatedPower createEvolvePower() {
        return new HeatedPower(this.owner, this.amount);
    }
}

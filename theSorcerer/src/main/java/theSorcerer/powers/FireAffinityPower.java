package theSorcerer.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.KirbyDeeMod;

public class FireAffinityPower extends AffinityPower {
    private static final Logger logger = LogManager.getLogger(FireAffinityPower.class.getName());
    private static final String POWER_NAME = "FireAffinityPower";
    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public FireAffinityPower(final AbstractCreature owner, final int amount) {
        super(owner, amount, POWER_ID, POWER_NAME);
    }

    @Override
    public AbstractPower makeCopy() {
        return new FireAffinityPower(owner, amount);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(IceAffinityPower.POWER_ID)) {
            logger.info("Ice Affinity applied, but Fire Affinity already existing -> remove Fire Affinity");
            reducePowerToZero();
            removeSelf();
        }
    }
}

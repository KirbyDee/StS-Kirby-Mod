package theSorcerer.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.KirbyDeeMod;

public class IceAffinityPower extends AffinityPower {
    private static final Logger logger = LogManager.getLogger(IceAffinityPower.class.getName());
    private static final String POWER_NAME = "IceAffinityPower";
    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public IceAffinityPower(final AbstractCreature owner, final int amount) {
        super(owner, amount, POWER_ID, POWER_NAME);
    }

    @Override
    public AbstractPower makeCopy() {
        return new IceAffinityPower(owner, amount);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(FireAffinityPower.POWER_ID)) {
            logger.info("Fire Affinity applied, but Ice Affinity already existing -> remove Ice Affinity");
            reducePowerToZero();
            removeSelf();
        }
    }
}

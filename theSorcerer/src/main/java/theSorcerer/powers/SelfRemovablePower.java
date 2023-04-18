package theSorcerer.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SelfRemovablePower extends DynamicPower {

    private static final Logger LOG = LogManager.getLogger(SelfRemovablePower.class.getName());

    public SelfRemovablePower(
            Class<? extends SelfRemovablePower> thisClazz,
            final AbstractCreature owner
    ) {
        super(thisClazz, owner);
    }

    public SelfRemovablePower(
            AbstractCreature owner,
            String powerID
    ) {
        super(owner, powerID);
    }

    public void removeSelf() {
        LOG.info("Remove " + this.ID);
        flash();
        addToBot(
                new RemoveSpecificPowerAction(
                        this.owner,
                        this.owner,
                        this.ID
                )
        );
    }
}

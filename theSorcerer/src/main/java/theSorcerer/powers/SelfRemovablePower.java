package theSorcerer.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SelfRemovablePower extends CustomPower {

    private static final Logger LOG = LogManager.getLogger(SelfRemovablePower.class.getName());

    public SelfRemovablePower(
            final AbstractCreature owner,
            String powerID
    ) {
        super(owner, powerID);
    }

    protected void removeSelf() {
        LOG.info("Remove " + this.ID);
        addToBot(
                new RemoveSpecificPowerAction(
                        this.owner,
                        this.owner,
                        this.ID
                )
        );
    }
}
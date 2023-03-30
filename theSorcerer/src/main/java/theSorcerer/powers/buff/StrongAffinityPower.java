package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.powers.SelfRemovablePower;

public abstract class StrongAffinityPower extends SelfRemovablePower {

    private static final Logger LOG = LogManager.getLogger(StrongAffinityPower.class.getName());

    public StrongAffinityPower(
            AbstractCreature owner,
            int amount,
            String powerId
    ) {
        super(owner, powerId);
        this.amount = amount;

        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    @Override
    public void atStartOfTurn() {
        if (this.amount > 0) {
            flash();
            applyAffinity();
        }
        removeSelf();
    }

    private void applyAffinity() {
        LOG.info("Apply affinity power: " + this.amount);
        for (int i = 0; i < this.amount; i++) {
            addToBot(
                    new ApplyPowerAction(
                            this.owner,
                            this.owner,
                            createAffinityPower(),
                            1
                    )
            );
        }
    }

    protected abstract ElementAffinityPower<?> createAffinityPower();

    @Override
    public void updateDescription() {
        this.description = descriptions[0] + this.amount + descriptions[1];
    }
}

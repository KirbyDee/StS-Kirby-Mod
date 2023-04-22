package theSorcerer.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class DynamicReducePerTurnPower extends DynamicAmountPower {

    private static final Logger LOG = LogManager.getLogger(DynamicReducePerTurnPower.class.getName());

    private boolean justApplied = false;

    protected boolean isSourceMonster;

    public DynamicReducePerTurnPower(
            Class<? extends DynamicReducePerTurnPower> thisClazz,
            AbstractCreature owner,
            int amount,
            boolean isSourceMonster
    ) {
        super(thisClazz, owner, amount);
        this.isTurnBased = true;
        this.canGoNegative = false;
        this.isSourceMonster = isSourceMonster;

        if (AbstractDungeon.actionManager.turnHasEnded && isSourceMonster && getPowerType() == PowerType.DEBUFF) {
            this.justApplied = true;
        }
    }

    @Override
    public void atEndOfRound() {
        LOG.info("End of turn, try reducing Power " + this.ID);
        if (this.justApplied) {
            LOG.info("Just applied, do nothing");
            this.justApplied = false;
        } else {
            if (this.amount == 1) {
                LOG.info("Remove Power");
                removeSelf();
            }
            else {
                LOG.info("Reduce by 1.");
                addToBot(
                        new ReducePowerAction(
                                this.owner,
                                this.owner,
                                this.ID,
                                1
                        )
                );
            }
        }
    }
}

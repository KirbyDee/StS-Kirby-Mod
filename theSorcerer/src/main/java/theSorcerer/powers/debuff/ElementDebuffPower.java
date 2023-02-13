package theSorcerer.powers.debuff;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.powers.DynamicPower;

public abstract class ElementDebuffPower extends DynamicPower {

    protected final boolean isSourceMonster;

    private boolean justApplied = false;

    public ElementDebuffPower(
            final AbstractCreature owner,
            final int amount,
            final String thisPowerID,
            boolean isSourceMonster
    ) {
        super(owner, thisPowerID);
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.canGoNegative = false;

        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }
        this.isSourceMonster = isSourceMonster;
        if (AbstractDungeon.actionManager.turnHasEnded && isSourceMonster) {
            this.justApplied = true;
        }

        updateDescription();
    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
        } else {
            if (this.amount == 0) {
                addToBot(
                        new RemoveSpecificPowerAction(
                                this.owner,
                                this.owner,
                                this.ID
                        )
                );
            }
            else {
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

    @Override
    public void updateDescription() {
        description = this.descriptions[0] + this.amount + this.descriptions[1];
    }
}

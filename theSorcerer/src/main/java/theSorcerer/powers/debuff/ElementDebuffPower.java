package theSorcerer.powers.debuff;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.powers.DynamicAmountPower;

public abstract class ElementDebuffPower extends DynamicAmountPower {

    protected final boolean isSourceMonster;

    private boolean justApplied = false;

    public ElementDebuffPower(
            Class<? extends ElementDebuffPower> thisClazz,
            AbstractCreature owner,
            int amount,
            boolean isSourceMonster
    ) {
        super(thisClazz, owner, amount);
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.canGoNegative = false;
        this.isSourceMonster = isSourceMonster;
        if (AbstractDungeon.actionManager.turnHasEnded && isSourceMonster) {
            this.justApplied = true;
        }

        updateDescription();
    }

    public ElementDebuffPower(
            final AbstractCreature owner,
            final int amount,
            final String thisPowerID,
            boolean isSourceMonster
    ) {
        super(owner, thisPowerID, amount);
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.canGoNegative = false;
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
                removeSelf();
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

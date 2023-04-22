package theSorcerer.powers.debuff;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.powers.DynamicAmountPower;
import theSorcerer.powers.DynamicReducePerTurnPower;

public abstract class ElementDebuffPower extends DynamicReducePerTurnPower {

    public ElementDebuffPower(
            Class<? extends ElementDebuffPower> thisClazz,
            AbstractCreature owner,
            int amount,
            boolean isSourceMonster
    ) {
        super(
                thisClazz,
                owner,
                amount,
                isSourceMonster
        );
        updateDescription();
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.DEBUFF;
    }

    @Override
    public void updateDescription() {
        description = this.descriptions[0] + this.amount + this.descriptions[1];
    }
}

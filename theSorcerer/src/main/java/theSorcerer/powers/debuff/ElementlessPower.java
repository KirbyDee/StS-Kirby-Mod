package theSorcerer.powers.debuff;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.DynamicReducePerTurnPower;

public class ElementlessPower extends DynamicReducePerTurnPower {

    public ElementlessPower(
            final AbstractCreature owner
    ) {
        this(owner, 1);
    }

    public ElementlessPower(
            AbstractCreature owner,
            int amount
    ) {
        super(
                ElementlessPower.class,
                owner,
                amount,
                false
        );

        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        DynamicDungeon.triggerOnElementless();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        DynamicDungeon.triggerOnElementless();
    }

    @Override
    public void updateDescription() {
        description = this.descriptions[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ElementlessPower(this.owner);
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.DEBUFF;
    }
}

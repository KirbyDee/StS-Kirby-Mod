package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.DynamicDungeon;

public class InnerFocusPower extends StartOfTurnPower {

    public InnerFocusPower(
            final AbstractCreature owner,
            final int amount
    ) {
        super(
                InnerFocusPower.class,
                owner,
                HeatedPower.class,
                amount
        );
    }

    @Override
    protected void applyPower() {
        DynamicDungeon.gainEnergy(this.amount);
    }

    @Override
    public void updateDescription() {
        this.description = this.descriptions[0] + (this.amount > 1 ? (this.descriptions[1] + this.amount + this.descriptions[2]) : this.descriptions[3]);
    }

    @Override
    public AbstractPower makeCopy() {
        return new InnerFocusPower(this.owner, this.amount);
    }
}


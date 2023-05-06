package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import theSorcerer.powers.debuff.ElementlessPower;

public class UneducatedGuessPower extends EndOfTurnPower {

    public UneducatedGuessPower(
            final AbstractCreature owner,
            final int amount
    ) {
        super(
                UneducatedGuessPower.class,
                owner,
                ElementlessPower.class,
                amount
        );
    }

    @Override
    protected void applyPower() {
        addToBot(
                new ApplyPowerAction(
                        this.owner,
                        this.owner,
                        new DrawCardNextTurnPower(this.owner, this.amount),
                        this.amount
                )
        );
    }

    @Override
    public void updateDescription() {
        super.updateDescription();
        this.description += (this.amount == 1 ? this.descriptions[2] : this.descriptions[3]) + this.descriptions[4];
    }

    @Override
    public AbstractPower makeCopy() {
        return new UneducatedGuessPower(this.owner, this.amount);
    }
}


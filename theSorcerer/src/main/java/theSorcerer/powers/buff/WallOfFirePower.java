package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WallOfFirePower extends EndOfTurnElementPower {

    public WallOfFirePower(
            final AbstractCreature owner,
            final int amount
    ) {
        super(
                WallOfFirePower.class,
                owner,
                HeatedPower.class,
                amount
        );
    }

    @Override
    protected void applyPower() {
        addToBot(
                new GainBlockAction(
                        this.owner,
                        this.owner,
                        this.amount
                )
        );
    }

    @Override
    public AbstractPower makeCopy() {
        return new WallOfFirePower(this.owner, this.amount);
    }
}


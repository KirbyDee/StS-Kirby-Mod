package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.DynamicAmountPower;

public class PastEmbracePower extends DynamicAmountPower {

    public PastEmbracePower(
            AbstractCreature owner,
            int amount
    ) {
        super(
                PastEmbracePower.class,
                owner,
                amount
        );

        updateDescription();
    }

    @Override
    public void triggerOnFlashback() {
        flash();
        DynamicDungeon.gainEnergy(this.amount);
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0];
        if (this.amount == 1) {
            this.description += descriptions[1];
        }
        else {
            this.description += descriptions[2] + this.amount + descriptions[3];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new PastEmbracePower(this.owner, this.amount);
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.BUFF;
    }
}

package theSorcerer.powers.buff;

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
        DynamicDungeon.drawCard(this.amount);
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0] + this.amount + (this.amount == 1 ? descriptions[2] : descriptions[3]);
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

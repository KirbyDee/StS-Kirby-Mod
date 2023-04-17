package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.KirbyDeeMod;
import theSorcerer.powers.DynamicPower;

public class PastEmbracePower extends DynamicPower {

    public static final String POWER_NAME = "PastEmbracePower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public PastEmbracePower(
            final AbstractCreature owner,
            final int amount
    ) {
        super(owner, POWER_ID);
        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }

        updateDescription();
    }

    @Override
    public void onSpecificTrigger() {
        flash();
        addToBot(new DrawCardAction(this.owner, this.amount));
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0] + this.amount;
        if (this.amount == 1) {
            this.description += descriptions[1];
        }
        else {
            this.description += descriptions[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new PastEmbracePower(this.owner, this.amount);
    }
}

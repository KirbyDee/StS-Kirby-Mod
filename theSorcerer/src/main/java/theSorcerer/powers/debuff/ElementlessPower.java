package theSorcerer.powers.debuff;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.KirbyDeeMod;
import theSorcerer.powers.DynamicAmountPower;

public class ElementlessPower extends DynamicAmountPower {

    private static final String POWER_NAME = "ElementlessPower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public ElementlessPower(
            final AbstractCreature owner
    ) {
        this(owner, 1);
    }

    public ElementlessPower(
            final AbstractCreature owner,
            final int amount
    ) {
        super(owner, POWER_ID, amount);
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.canGoNegative = false;

        updateDescription();
    }

    @Override
    public void atEndOfRound() {
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

    @Override
    public void updateDescription() {
        description = this.descriptions[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ElementlessPower(this.owner);
    }
}

package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.KirbyDeeMod;
import theSorcerer.powers.DynamicPower;

public class WallOfFirePower extends EndOfTurnElementPower {

    private static final String POWER_NAME = "WallOfFirePower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public WallOfFirePower(
            final AbstractCreature owner,
            final int amount
    ) {
        super(
                owner,
                POWER_ID,
                DynamicPower.getID(HeatedPower.class),
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


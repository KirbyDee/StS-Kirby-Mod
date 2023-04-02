package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import theSorcerer.KirbyDeeMod;
import theSorcerer.powers.debuff.ElementlessPower;

public class UneducatedGuessPower extends EndOfTurnElementPower {

    private static final String POWER_NAME = "UneducatedGuessPower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public UneducatedGuessPower(
            final AbstractCreature owner,
            final int amount
    ) {
        super(
                owner,
                POWER_ID,
                ElementlessPower.POWER_ID,
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
    public AbstractPower makeCopy() {
        return new UneducatedGuessPower(this.owner, this.amount);
    }
}


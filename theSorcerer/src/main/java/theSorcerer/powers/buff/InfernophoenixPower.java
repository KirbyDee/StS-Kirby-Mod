package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.KirbyDeeMod;
import theSorcerer.powers.SelfRemovablePower;

public class InfernophoenixPower extends SelfRemovablePower {

    public static final String POWER_NAME = "InfernophoenixPower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public InfernophoenixPower(
            AbstractCreature owner
    ) {
        super(owner, POWER_ID);
        this.amount = 0;

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (this.amount > 0) {
            flash();
            AbstractDungeon.player.heal(this.amount, true);
        }
        removeSelf();
    }

    @Override
    public int onLoseHp(int damageAmount) {
        this.amount += damageAmount;
        updateDescription();

        return damageAmount;
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0] + this.amount + descriptions[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new InfernophoenixPower(this.owner);
    }
}
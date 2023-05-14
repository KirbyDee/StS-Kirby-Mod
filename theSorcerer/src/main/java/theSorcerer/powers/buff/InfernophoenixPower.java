package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.powers.SelfRemovablePower;

public class InfernophoenixPower extends SelfRemovablePower {

    public InfernophoenixPower(
            AbstractCreature owner
    ) {
        super(
                InfernophoenixPower.class,
                owner
        );
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
        flash();
        addToBot(new MakeTempCardInDiscardAction(new Burn(), 1));
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

    @Override
    public PowerType getPowerType() {
        return PowerType.BUFF;
    }
}

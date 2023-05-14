package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.cards.status.Frostbite;
import theSorcerer.powers.SelfRemovablePower;

public class CryophoenixPower extends SelfRemovablePower {

    public CryophoenixPower(
            final AbstractCreature owner
    ) {
        super(
                CryophoenixPower.class,
                owner
        );
        this.amount = 0;

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        removeSelf();
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        flash();
        addToBot(new MakeTempCardInDiscardAction(new Frostbite(), 1));
        addToTop(
                new DamageAction(
                        info.owner,
                        new DamageInfo(this.owner, info.output, DamageInfo.DamageType.THORNS),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT
                )
        );

        return damageAmount;
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CryophoenixPower(this.owner);
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.BUFF;
    }
}

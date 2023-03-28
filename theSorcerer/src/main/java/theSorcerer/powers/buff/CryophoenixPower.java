package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.KirbyDeeMod;
import theSorcerer.powers.SelfRemovablePower;

public class CryophoenixPower extends SelfRemovablePower {

    public static final String POWER_NAME = "CryophoenixPower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public CryophoenixPower(
            final AbstractCreature owner
    ) {
        super(owner, POWER_ID);
        this.amount = 0;

        updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        addToBot(
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
}

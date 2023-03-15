package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.KirbyDeeMod;

public class FrostShockPower extends EndOfTurnElementPower {

    private static final String POWER_NAME = "FrostShockPower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public FrostShockPower(
            final AbstractCreature owner,
            final int amount
    ) {
        super(
                owner,
                POWER_ID,
                ChilledPower.POWER_ID,
                amount
        );
    }

    @Override
    protected void applyPower() {
        AbstractCreature target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (target != null) {
            addToBot(
                    new DamageAction(
                            target,
                            new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS),
                            AbstractGameAction.AttackEffect.NONE
                    )
            );
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new WallOfFirePower(this.owner, this.amount);
    }
}


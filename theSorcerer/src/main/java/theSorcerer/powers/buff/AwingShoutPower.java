package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theSorcerer.powers.DynamicAmountPower;

public class AwingShoutPower extends DynamicAmountPower {

    public AwingShoutPower(
            AbstractCreature owner,
            int amount
    ) {
        super(
                AwingShoutPower.class,
                owner,
                amount
        );

        updateDescription();
    }

    @Override
    public void triggerOnElementless() {
        flash();
        AbstractDungeon.getCurrRoom().monsters.monsters.forEach(this::applyWeakAndVulnerable);
    }

    private void applyWeakAndVulnerable(AbstractMonster monster) {
        // weak
        addToBot(
                new ApplyPowerAction(
                        monster,
                        this.owner,
                        new WeakPower(
                                monster,
                                this.amount,
                                false
                        ),
                        this.amount,
                        true,
                        AbstractGameAction.AttackEffect.NONE
                )
        );

        // vulnerable
        addToBot(
                new ApplyPowerAction(
                        monster,
                        this.owner,
                        new VulnerablePower(
                                monster,
                                this.amount,
                                false
                        ),
                        this.amount,
                        true,
                        AbstractGameAction.AttackEffect.NONE
                )
        );
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0] + this.amount + descriptions[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AwingShoutPower(this.owner, this.amount);
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.BUFF;
    }
}

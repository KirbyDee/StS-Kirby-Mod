package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.debuff.FrozenPower;

public class ShatterAction extends AbstractGameAction {

    private final AbstractPlayer player;

    private final AbstractMonster monster;

    public ShatterAction(
            AbstractPlayer player,
            AbstractMonster monster
    ) {
        this.player = player;
        this.monster = monster;
        this.actionType = ActionType.DEBUFF;
    }

    @Override
    public void update() {
        DynamicDungeon.withFrozenDo(
                monster,
                this::withFrozen
        );

        this.isDone = true;
    }

    private void withFrozen(final FrozenPower power) {
        final int amount = power.amount;
        if (amount <= 0) {
            return;
        }

        DynamicDungeon.withAllMonstersDo(m ->
                addToBot(
                        new ApplyPowerAction(
                                m,
                                this.player,
                                new WeakPower(m, amount, false),
                                amount,
                                true,
                                AbstractGameAction.AttackEffect.NONE
                        )
                )
        );
    }
}
package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
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
        this.amount = power.amount;
        if (amount <= 0) {
            return;
        }

        DynamicDungeon.withAllMonstersDo(this::applyWeakAndVulnerable);
    }

    private void applyWeakAndVulnerable(AbstractMonster monster) {
        // weak
        addToBot(
                new ApplyPowerAction(
                        monster,
                        this.player,
                        new WeakPower(
                                monster,
                                this.amount,
                                false
                        ),
                        this.amount,
                        true
                )
        );

        // vulnerable
        addToBot(
                new ApplyPowerAction(
                        monster,
                        this.player,
                        new VulnerablePower(
                                monster,
                                this.amount,
                                false
                        ),
                        this.amount,
                        true
                )
        );
    }
}

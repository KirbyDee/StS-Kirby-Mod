package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.debuff.AblazePower;

public class ExplosionAction extends AbstractGameAction {

    private final AbstractPlayer player;

    private final AbstractMonster monster;

    public ExplosionAction(
            AbstractPlayer player,
            AbstractMonster monster
    ) {
        this.player = player;
        this.monster = monster;
    }

    @Override
    public void update() {
        DynamicDungeon.withAblaze(
                monster,
                this::withAblaze
        );

        this.isDone = true;
    }

    private void withAblaze(final AblazePower power) {
        final int amount = power.amount;
        if (amount <= 0) {
            return;
        }

        DynamicDungeon.withAllMonsters(m ->
                addToBot(
                        new DamageAction(
                                m,
                                new DamageInfo(this.player, amount, DamageInfo.DamageType.NORMAL),
                                AttackEffect.FIRE
                        )
                )
        );
    }
}

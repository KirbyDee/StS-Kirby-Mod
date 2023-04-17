package theSorcerer.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.List;

public class DamageMultipleEnemiesAction extends AbstractGameAction {

    private int[] damage;
    private boolean firstFrame;

    public DamageMultipleEnemiesAction(
            AbstractCreature source,
            int[] amount,
            DamageInfo.DamageType type,
            AbstractGameAction.AttackEffect effect
    ) {
        this.firstFrame = true;
        this.source = source;
        this.damage = amount;
        this.actionType = ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.firstFrame) {
            onFirstFrame();
        }

        tickDuration();

        if (this.isDone) {
            onDone();
        }
    }

    private void onFirstFrame() {
        boolean playedMusic = false;

        List<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
        for (int i = 0; i < monsters.size(); i++) {
            AbstractMonster monster = monsters.get(i);
            if (this.damage[i] < 0 || invalidMonsterForDamage(monster)) {
                continue;
            }

            AbstractDungeon.effectList.add(
                    new FlashAtkImgEffect(
                            monster.hb.cX,
                            monster.hb.cY,
                            this.attackEffect,
                            !playedMusic
                    )
            );
            playedMusic = true;
        }

        this.firstFrame = false;
    }

    private boolean invalidMonsterForDamage(final AbstractMonster monster) {
        return monster.isDying || monster.currentHealth <= 0 || monster.isEscaping;
    }

    private void onDone() {
        AbstractDungeon.player.powers.forEach(p -> p.onDamageAllEnemies(this.damage));

        List<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
        for (int i = 0; i < monsters.size(); i++) {
            AbstractMonster monster = monsters.get(i);
            final int damageToDo = this.damage[i];
            if (monster.isDeadOrEscaped() || damageToDo < 0) {
                continue;
            }

            if (this.attackEffect == AttackEffect.POISON) {
                monster.tint.color.set(Color.CHARTREUSE);
                monster.tint.changeColor(Color.WHITE.cpy());
            }
            else if (this.attackEffect == AttackEffect.FIRE) {
                monster.tint.color.set(Color.RED);
                monster.tint.changeColor(Color.WHITE.cpy());
            }

            monster.damage(
                    new DamageInfo(
                            this.source,
                            damageToDo,
                            this.damageType
                    )
            );
        }

        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }

        if (!Settings.FAST_MODE) {
            addToTop(new WaitAction(0.1F));
        }
    }
}
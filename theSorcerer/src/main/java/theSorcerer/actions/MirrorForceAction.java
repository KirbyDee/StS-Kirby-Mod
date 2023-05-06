package theSorcerer.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import theSorcerer.patches.monsters.AbstractMonsterPatch;

public class MirrorForceAction extends AbstractGameAction {

    private static final String TEXT = "That enemy does not intend to attack!"; // TODOO

    private final AbstractPlayer player;
    private final AbstractMonster targetMonster;

    private final boolean upgraded;

    public MirrorForceAction(
            AbstractPlayer player,
            AbstractMonster targetMonster,
            boolean upgraded
    ) {
        this.player = player;
        this.targetMonster = targetMonster;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        if (this.targetMonster.getIntentBaseDmg() >= 0) {
            doDamage(AbstractMonsterPatch.moveInfo.get(this.targetMonster));
        }
        else {
            AbstractDungeon.effectList.add(new ThoughtBubble(this.player.dialogX, this.player.dialogY, 3.0F, TEXT, true));
        }

        this.isDone = true;
    }

    private void doDamage(final EnemyMoveInfo enemyMoveInfo) {
        addToBot(
                new VFXAction(
                        new ShockWaveEffect(
                                this.player.hb.cX,
                                this.player.hb.cY,
                                Color.GOLD,
                                ShockWaveEffect.ShockWaveType.NORMAL
                        )
                )
        );

        if (enemyMoveInfo.isMultiDamage) {
            for (int i = 0; i < enemyMoveInfo.multiplier; i++) {
                addToBot(new WaitAction(0.25F));
                damageEnemy(this.targetMonster.getIntentDmg());
            }
        }
        else {
            damageEnemy(this.targetMonster.getIntentDmg());
        }
    }

    private void damageEnemy(final int damage) {
        if (this.upgraded) {
            addToBot(
                    new DamageAllEnemiesAction(
                            this.player,
                            damage,
                            DamageInfo.DamageType.NORMAL,
                            AbstractGameAction.AttackEffect.NONE
                    )
            );
        }
        else {
            addToBot(
                    new DamageAction(
                            this.targetMonster,
                            new DamageInfo(this.player, damage, DamageInfo.DamageType.NORMAL),
                            AbstractGameAction.AttackEffect.NONE
                    )
            );
        }
    }
}

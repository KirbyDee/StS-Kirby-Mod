package theSorcerer.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class IgniteLoseHpAction extends AbstractGameAction {

    public IgniteLoseHpAction(
            AbstractCreature target,
            AbstractCreature source,
            int amount,
            AbstractGameAction.AttackEffect effect
    ) {
        setValues(target, source, amount);
        this.attackEffect = effect;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            this.isDone = true;
        }
        else {
            if (this.duration == 0.33F && this.target.currentHealth > 0) {
                AbstractDungeon.effectList.add(
                        new FlashAtkImgEffect(
                                this.target.hb.cX,
                                this.target.hb.cY,
                                this.attackEffect
                        )
                );
            }

            tickDuration();
            if (this.isDone) {
                if (this.target.currentHealth > 0) {
                    this.target.tint.color = Color.SCARLET.cpy();
                    this.target.tint.changeColor(Color.WHITE.cpy());
                    this.target.damage(
                            new DamageInfo(
                                    this.source,
                                    this.amount,
                                    DamageInfo.DamageType.HP_LOSS
                            )
                    );
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }

                this.addToTop(new WaitAction(0.1F));
            }

        }
    }
}

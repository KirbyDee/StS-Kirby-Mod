package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theSorcerer.cards.Decay;

public class DecayAction extends AbstractGameAction {

    private final DamageInfo info;

    private final boolean upgraded;

    public DecayAction(
            AbstractCreature target,
            boolean upgraded,
            DamageInfo info
    ) {
        this.info = info;
        this.upgraded = upgraded;
        setValues(target, info);
        this.actionType = ActionType.DAMAGE;
    }

    public void update() {
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.POISON));
        this.target.damage(this.info);
        if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
            AbstractCard card = new Decay();
            card.setCostForTurn(0);
            if (this.upgraded) {
                card.upgrade();
            }
            addToBot(
                    new MakeTempCardInHandAction(
                            card,
                            1
                    )
            );
        }

        if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
        }

        this.isDone = true;
    }
}

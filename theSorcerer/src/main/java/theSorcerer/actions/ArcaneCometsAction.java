package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.effect.ArcaneCometsEffect;

public class ArcaneCometsAction extends AbstractGameAction {

    private final AbstractCard card;

    private final ArcaneCometsEffect.Element element;

    public ArcaneCometsAction(AbstractCard card) {
        this.card = card;
        if (DynamicDungeon.isHeated()) {
            this.element = ArcaneCometsEffect.Element.FIRE;
        }
        else if (DynamicDungeon.isChilled()) {
            this.element = ArcaneCometsEffect.Element.ICE;
        }
        else {
            this.element = ArcaneCometsEffect.Element.NONE;
        }
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            this.card.calculateCardDamage((AbstractMonster) this.target);
            addToTop(new DamageAction(this.target, new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn), AttackEffect.NONE));
            addToTop(new VFXAction(new ArcaneCometsEffect(this.target.hb.cX, this.target.hb.cY, this.element)));
            addToTop(new WaitAction(0.125F));
        }

        this.isDone = true;
    }
}
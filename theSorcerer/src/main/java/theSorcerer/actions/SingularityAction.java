package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theSorcerer.effect.DarkLightningEffect;

public class SingularityAction extends AttackDamageRandomEnemyAction {

    public SingularityAction(AbstractCard card) {
        super(card);
    }

    @Override
    public void update() {
        if (!Settings.FAST_MODE) {
            addToTop(new WaitAction(0.1F));
        }

        super.update();
        if (this.target != null) {
            addToTop(new VFXAction(new DarkLightningEffect(this.target.drawX, this.target.drawY)));
            addToTop(new VFXAction(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect)));
            addToTop(new SFXAction("ORB_DARK_EVOKE", 0.1F));
            addToTop(new SFXAction("ORB_DARK_EVOKE", 0.5F));
            addToTop(new SFXAction("ORB_DARK_CHANNEL", 0.1F));
            addToTop(new SFXAction("ORB_DARK_CHANNEL", 0.5F));
            addToTop(new SFXAction("ORB_PLASMA_EVOKE", 0.5F));
        }
    }
}

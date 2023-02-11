package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import theSorcerer.cards.fire.SorcererFireCard;

public class Ice extends SorcererIceCard {

    // --- VALUES START ---
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int COST = 1;
    private static final int DAMAGE = 1;
    private static final int UPGRADE_PLUS_DMG = 2;
    // --- VALUES END ---

    public Ice() {
        super(
                Ice.class,
                COST,
                CardType.ATTACK,
                RARITY,
                TARGET
        );

        baseDamage = DAMAGE;
    }

    @Override
    public void onCardUse(AbstractPlayer p, AbstractMonster m) {
        // effect
        if (m != null) {
            if (Settings.FAST_MODE) {
                addToBot(new VFXAction(new FireBurstParticleEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale), 0.1F));
            }
            else {
                addToBot(new VFXAction(new FireBurstParticleEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale), 0.3F));
            }
        }

        // damage
        addToBot(
                new DamageAction(
                        m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.LIGHTNING
                )
        );
        addToBot(
                new GainBlockAction(
                        p,
                        2
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_PLUS_DMG);
    }
}

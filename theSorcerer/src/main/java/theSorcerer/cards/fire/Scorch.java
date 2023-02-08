package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;

public class Scorch extends SorcererFireCard {

    // --- VALUES START ---
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 2;
    // --- VALUES END ---

    public Scorch() {
        super(
                Scorch.class,
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
                        AbstractGameAction.AttackEffect.FIRE
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_PLUS_DMG);
    }
}

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
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCardTags;

public class Ice extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 0;
    private static final int BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    // --- VALUES END ---

    public Ice() {
        super(
                DynamicCard.InfoBuilder(Ice.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.BASIC)
                        .target(CardTarget.ENEMY)
                        .tags(SorcererCardTags.FLASHBACK)
                        .block(BLOCK)
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
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
        upgradeBlock(UPGRADE_PLUS_BLOCK);
    }
}

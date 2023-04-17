package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.patches.cards.CardAbility;

public class Scorch extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 0;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;
    // --- VALUES END ---

    public Scorch() {
        super(
                DynamicCard.InfoBuilder(Scorch.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.ENEMY)
                        .abilities(CardAbility.FLASHBACK)
                        .damage(DAMAGE)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // effect
        if (monster != null) {
            if (Settings.FAST_MODE) {
                addToBot(new VFXAction(new FireBurstParticleEffect(monster.hb.cX, monster.hb.cY - 40.0F * Settings.scale), 0.1F));
            }
            else {
                addToBot(new VFXAction(new FireBurstParticleEffect(monster.hb.cX, monster.hb.cY - 40.0F * Settings.scale), 0.3F));
            }
        }

        // damage
        addToBot(
                new DamageAction(
                        monster,
                        new DamageInfo(player, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_PLUS_DMG);
    }
}

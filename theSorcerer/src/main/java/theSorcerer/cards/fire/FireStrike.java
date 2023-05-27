package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.effect.FireParticleEffect;

public class FireStrike extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;
    // --- VALUES END ---

    public FireStrike() {
        super(
                DynamicCard.InfoBuilder(FireStrike.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.BASIC)
                        .target(CardTarget.ENEMY)
                        .tags(CardTags.STRIKE)
                        .damage(DAMAGE)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // damage
        addToBot(
                new DamageAction(
                        monster,
                        new DamageInfo(
                                player,
                                this.damage,
                                this.damageTypeForTurn
                        ),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT
                )
        );

        // effect
        if (monster != null) {
            if (Settings.FAST_MODE) {
                addToBot(new VFXAction(new FireParticleEffect(monster.hb.cX, monster.hb.cY - 40.0F * Settings.scale), 0.1F));
            }
            else {
                addToBot(new VFXAction(new FireParticleEffect(monster.hb.cX, monster.hb.cY - 40.0F * Settings.scale), 0.3F));
            }
        }
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_PLUS_DMG);
    }
}

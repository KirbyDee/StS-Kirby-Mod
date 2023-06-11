package theSorcerer.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.arcane.ArcaneStrike;
import theSorcerer.cards.fire.FireStrike;
import theSorcerer.cards.ice.IceStrike;

public class Strike_Yellow extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 3;
    // --- VALUES END ---

    public Strike_Yellow() {
        super(
                DynamicCard.InfoBuilder(Strike_Yellow.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.BASIC)
                        .target(CardTarget.ENEMY)
                        .tags(CardTags.STARTER_STRIKE, CardTags.STRIKE)
                        .damage(DAMAGE)
        );
    }

    public void use(AbstractPlayer player, AbstractMonster monster) {
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
    }

    @Override
    public void triggerOnMakeFire() {
        triggerOnMorphose(new FireStrike());
    }

    @Override
    public void triggerOnMakeIce() {
        triggerOnMorphose(new IceStrike());
    }

    @Override
    public void triggerOnMakeArcane() {
        triggerOnMorphose(new ArcaneStrike());
    }

    private void triggerOnMorphose(final CustomCard strike) {
        this.name = strike.name;
        this.loadCardImage(strike.textureImg);
    }

    public void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}
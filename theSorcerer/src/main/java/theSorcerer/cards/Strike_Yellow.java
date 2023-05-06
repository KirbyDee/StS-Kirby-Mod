package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

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
                        .rarity(CardRarity.SPECIAL)
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

    public void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}
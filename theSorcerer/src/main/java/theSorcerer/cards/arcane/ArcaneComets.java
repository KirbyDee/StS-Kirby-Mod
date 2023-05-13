package theSorcerer.cards.arcane;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;

public class ArcaneComets extends SorcererArcaneCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int DAMAGE = 2;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int DAMAGE_TIMES = 4;
    // --- VALUES END ---

    public ArcaneComets() {
        super(
                DynamicCard.InfoBuilder(ArcaneComets.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.UNCOMMON)
                        .damage(DAMAGE)
                        .magicNumber(DAMAGE_TIMES)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        for (int i = 0; i < this.magicNumber; ++i) {
            addToBot(
                    new AttackDamageRandomEnemyAction(
                            this,
                            AbstractGameAction.AttackEffect.LIGHTNING
                    )
            );
        }
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}

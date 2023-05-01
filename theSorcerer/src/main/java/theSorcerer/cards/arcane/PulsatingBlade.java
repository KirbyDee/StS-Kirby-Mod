package theSorcerer.cards.arcane;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.PulsatingBladeAction;
import theSorcerer.cards.DynamicCard;
import theSorcerer.patches.cards.CardAbility;

public class PulsatingBlade extends SorcererArcaneCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 12;
    // --- VALUES END ---

    public PulsatingBlade() {
        super(
                DynamicCard.InfoBuilder(PulsatingBlade.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.RARE)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
                        .abilities(CardAbility.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new PulsatingBladeAction()
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeBaseCost(UPGRADE_COST);
        upgradeDamage(UPGRADE_DAMAGE);
    }
}

package theSorcerer.cards.arcane;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.PulsatingBladeAction;
import theSorcerer.cards.DynamicCard;
import theSorcerer.modifiers.CardModifier;

public class PulsatingBlade extends SorcererArcaneCard {

    // --- VALUES START ---
    private static final int COST = 1;
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
                        .modifiers(CardModifier.EXHAUST)
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
        upgradeDamage(UPGRADE_DAMAGE);
    }
}

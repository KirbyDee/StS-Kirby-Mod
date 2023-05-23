package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.ElementalPathAction;
import theSorcerer.modifiers.CardModifier;

public class ElementalPath extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    // --- VALUES END ---

    public ElementalPath() {
        super(
                DynamicCard.InfoBuilder(ElementalPath.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.RARE)
                        .target(CardTarget.SELF)
                        .modifiers(CardModifier.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ElementalPathAction()
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeBaseCost(UPGRADE_COST);
    }
}

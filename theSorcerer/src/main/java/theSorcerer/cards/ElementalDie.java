package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.ElementalDieAction;

public class ElementalDie extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int CARDS_TO_PUT_IN_HAND = 1;
    // --- VALUES END ---

    public ElementalDie() {
        super(
                DynamicCard.InfoBuilder(ElementalDie.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ElementalDieAction(
                        CARDS_TO_PUT_IN_HAND
                )
        );
    }

    @Override
    public void upgradeValues() {
        upgradeBaseCost(UPGRADE_COST);
    }
}

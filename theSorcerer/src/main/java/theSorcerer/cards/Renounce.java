package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;

public class Renounce extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 0;
    private static final int CARD_AMOUNT_DRAW = 1;
    private static final int ENERGY_GAIN = 1;
    // --- VALUES END ---

    public Renounce() {
        super(
                DynamicCard.InfoBuilder(Renounce.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.RARE)
                        .magicNumber(CARD_AMOUNT_DRAW)
                        .secondMagicNumber(ENERGY_GAIN)
                        .modifiers(CardModifier.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        int amount = DynamicDungeon.getElementAmount();

        // draw cards
        DynamicDungeon.drawCard(this.magicNumber * amount);

        // gain energy
        DynamicDungeon.gainEnergy(this.magicNumber * amount);

        // lose all elements
        DynamicDungeon.loseAllElements();

        // apply elementless
        DynamicDungeon.applyElementless();
    }
}

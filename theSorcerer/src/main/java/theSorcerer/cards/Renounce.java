package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;

public class Renounce extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = -1;
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
                        .modifiers(CardModifier.EXHAUST, CardModifier.ELEMENTCOST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        int heatedAmount = DynamicDungeon.getHeatedAmount();
        int chilledAmount = DynamicDungeon.getChilledAmount();
        if (this.upgraded) {
            heatedAmount = DynamicDungeon.getElementAmount();
            chilledAmount = heatedAmount;
        }

        if (heatedAmount > 0) {
            DynamicDungeon.gainEnergy(this.magicNumber * heatedAmount);
        }
        if (chilledAmount > 0) {
            DynamicDungeon.drawCard(this.magicNumber * chilledAmount);
        }
    }
}

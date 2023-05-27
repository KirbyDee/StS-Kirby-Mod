package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;
import theSorcerer.util.ElementAmount;

public class Renounce extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = -1;
    private static final int CARD_AMOUNT_DRAW = 1;
    private static final int ENERGY_GAIN = 1;
    // --- VALUES END ---

    private ElementAmount elementAmountSpend = ElementAmount.empty();

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
    public void triggerOnElementCost(final ElementAmount elementAmountSpend) {
        this.elementAmountSpend = elementAmountSpend;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        if (this.elementAmountSpend.isHeated() || (this.upgraded && this.elementAmountSpend.hasAmount())) {
            DynamicDungeon.gainEnergy(this.magicNumber * this.elementAmountSpend.getAmount());
        }
        if (this.elementAmountSpend.isChilled() || (this.upgraded && this.elementAmountSpend.hasAmount())) {
            DynamicDungeon.drawCard(this.magicNumber * this.elementAmountSpend.getAmount());
        }

        this.elementAmountSpend = ElementAmount.empty();
    }
}

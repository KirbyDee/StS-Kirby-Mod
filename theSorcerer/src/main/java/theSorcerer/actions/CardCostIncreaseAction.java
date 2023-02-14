package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class CardCostIncreaseAction extends CardChooseAction {

    // --- VALUES START ---
    private final static String TEXT = "increase Energy Cost by ";
    private final int costIncrease;
    // --- VALUES END ---

    public CardCostIncreaseAction(
            final int cardAmount,
            final int costIncrease
    ) {
        super(cardAmount);
        this.costIncrease = costIncrease;
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return card.costForTurn >= 0 &&
                super.canBeChosen(card);
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        card.setCostForTurn(card.costForTurn + this.costIncrease);
    }

    @Override
    protected String getChooseText() {
        return TEXT + this.costIncrease;
    }
}

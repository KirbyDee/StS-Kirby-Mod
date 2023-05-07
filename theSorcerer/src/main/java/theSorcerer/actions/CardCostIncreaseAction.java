package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class CardCostIncreaseAction extends HandCardChooseAction {

    // --- VALUES START ---
    private final static String TEXT = "increase Energy Cost by "; // TODOO
    private final int costIncrease;
    // --- VALUES END ---

    public CardCostIncreaseAction(
            final int cardAmount,
            final int costIncrease
    ) {
        super(cardAmount);
        this.costIncrease = costIncrease;
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return card.costForTurn >= 0 &&
                super.canBeChosen(card);
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        card.modifyCostForCombat(this.costIncrease);
        this.player.hand.addToTop(card);
    }

    @Override
    protected String getChooseText() {
        return TEXT + this.costIncrease + ".";
    }
}

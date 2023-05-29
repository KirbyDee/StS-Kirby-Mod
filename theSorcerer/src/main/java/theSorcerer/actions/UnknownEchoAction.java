package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.DynamicDungeon;

public class UnknownEchoAction extends HandCardChooseAction {

    // --- VALUES START ---
    private final static String TEXT = "put on top of your draw pile."; // TODOO
    // --- VALUES END ---

    public UnknownEchoAction(int amount) {
        super(amount);
        this.actionType = ActionType.ENERGY;
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return true;
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        this.player.hand.moveToDeck(card, false);
        if (card.costForTurn > 0) {
            DynamicDungeon.gainEnergy(card.costForTurn);
        }
    }

    @Override
    protected String getChooseText() {
        return TEXT;
    }
}

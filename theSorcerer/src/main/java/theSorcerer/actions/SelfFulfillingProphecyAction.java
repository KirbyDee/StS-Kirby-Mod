package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.DynamicDungeon;

public class SelfFulfillingProphecyAction extends HandCardChooseAction {

    // --- VALUES START ---
    private final static String TEXT = "give Futurity and put to bottom of your draw pile."; // TODOO
    // --- VALUES END ---

    public SelfFulfillingProphecyAction(int amount) {
        super(amount);
        this.actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        DynamicDungeon.makeCardFuturity(card);
        this.player.hand.moveToBottomOfDeck(card);
    }

    @Override
    protected String getChooseText() {
        return TEXT;
    }
}

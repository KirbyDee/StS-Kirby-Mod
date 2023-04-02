package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.DynamicDungeon;

public class UnknownEchoAction extends HandCardChooseAction {

    // --- VALUES START ---
    private final static String TEXT = "put on top of your draw pile."; // TODO
    // --- VALUES END ---

    public UnknownEchoAction(int amount) {
        super(amount);
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        this.player.hand.moveToDeck(card, false);
        DynamicDungeon.gainEnergy(card.costForTurn);
    }

    @Override
    protected String getChooseText() {
        return TEXT;
    }
}

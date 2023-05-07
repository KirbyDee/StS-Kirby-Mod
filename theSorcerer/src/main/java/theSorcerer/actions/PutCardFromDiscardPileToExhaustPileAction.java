package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PutCardFromDiscardPileToExhaustPileAction extends DiscardPileCardChooseAction {

    // --- VALUES START ---
    private final static String[] TEXTS = { // TODOO
            "Choose ",
            " card ",
            " cards ",
            "to move to the exhaust pile."
    };
    // --- VALUES END ---

    public PutCardFromDiscardPileToExhaustPileAction(final int amount) {
        super(amount);
        this.actionType = ActionType.EXHAUST;
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return true;
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        AbstractDungeon.player.discardPile.moveToExhaustPile(card);
    }

    @Override
    protected String getChooseText() {
        return TEXTS[0] + this.amount + (this.amount == 1 ? TEXTS[1] : TEXTS[2]) + TEXTS[3];
    }
}

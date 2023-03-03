package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PutCardFromDrawPileToDiscardPileAction extends DrawPileCardChooseAction {

    // --- VALUES START ---
    private final static String[] TEXTS = { // TODO
            "Choose ",
            " card ",
            " cards ",
            "to move to the discard pile."
    };
    // --- VALUES END ---

    public PutCardFromDrawPileToDiscardPileAction(final int amount) {
        super(amount);
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return true;
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        AbstractDungeon.player.drawPile.moveToDiscardPile(card);
    }

    @Override
    protected String getChooseText() {
        return TEXTS[0] + this.amount + (this.amount == 1 ? TEXTS[1] : TEXTS[2]) + TEXTS[3];
    }
}

package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;

public class PutCardFromExhaustPileToDiscardPileAndGiveFlashbackAction extends ExhaustPileCardChooseAction {

    // --- VALUES START ---
    private final static String[] TEXTS = { // TODO
            "Choose ",
            " card ",
            " cards ",
            "to move to the discard pile and give #yFlashback."
    };
    // --- VALUES END ---

    public PutCardFromExhaustPileToDiscardPileAndGiveFlashbackAction(final int amount) {
        super(amount);
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return true;
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        AbstractDungeon.player.exhaustPile.moveToDiscardPile(card);
        DynamicDungeon.makeCardFlashback(card);
    }

    @Override
    protected String getChooseText() {
        return TEXTS[0] + this.amount + (this.amount == 1 ? TEXTS[1] : TEXTS[2]) + TEXTS[3];
    }
}

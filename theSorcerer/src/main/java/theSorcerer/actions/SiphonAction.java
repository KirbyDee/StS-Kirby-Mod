package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;

public class SiphonAction extends DiscardPileCardChooseAction {

    // --- VALUES START ---
    private final static String[] TEXTS = { // TODOO
            "Choose ",
            " card ",
            "up to ",
            " cards ",
            "to move to the exhaust pile."
    };
    // --- VALUES END ---

    public SiphonAction(int amount) {
        super(amount);
        this.actionType = ActionType.ENERGY;
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        AbstractDungeon.player.discardPile.moveToExhaustPile(card);
        if (card.costForTurn > 0) {
            DynamicDungeon.gainEnergy(card.costForTurn);
        }
    }

    @Override
    protected String getChooseText() {
        String text = TEXTS[0];
        if (this.anyNumber) {
            text += TEXTS[2] + this.amount + TEXTS[3] + TEXTS[4];
        }
        else if (this.amount == 1) {
            text += this.amount + TEXTS[1] + TEXTS[4];
        }
        else {
            text += this.amount + TEXTS[3] + TEXTS[4];
        }
        return text;
    }
}

package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardedDefenseAction extends HandCardChooseAction {

    // --- VALUES START ---
    private final static String TEXT = "discard."; // TODO
    private final AbstractPlayer p;
    private final int block;
    // --- VALUES END ---

    public DiscardedDefenseAction(final AbstractPlayer p, final int block) {
        super(true);
        this.p = p;
        this.block = block;
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return true;
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        AbstractDungeon.player.hand.moveToDiscardPile(card);
        GameActionManager.incrementDiscard(false);
        card.triggerOnManualDiscard();
    }

    @Override
    protected void onActionDone() {
        for (int i = 0; i < GameActionManager.totalDiscardedThisTurn; i++) {
            addToBot(new GainBlockAction(p, p, this.block));
            addToBot(new WaitAction(0.125F));
        }
    }

    @Override
    protected String getChooseText() {
        return TEXT;
    }
}

package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardedDefenseAction extends HandCardChooseAction {

    // --- VALUES START ---
    private final static String TEXT = "discard."; // TODOO
    private final AbstractPlayer player;
    private final int block;
    // --- VALUES END ---

    public DiscardedDefenseAction(final AbstractPlayer player, final int block) {
        super(true);
        this.player = player;
        this.block = block;
        this.actionType = ActionType.DISCARD;
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return true;
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        this.player.hand.moveToDiscardPile(card);
        card.triggerOnManualDiscard();
        GameActionManager.incrementDiscard(false);
    }

    @Override
    protected void onActionDone() {
        super.onActionDone();
        for (int i = 0; i < GameActionManager.totalDiscardedThisTurn; i++) {
            addToBot(new GainBlockAction(this.player, this.player, this.block));
            addToBot(new WaitAction(0.125F));
        }
    }

    @Override
    protected String getChooseText() {
        return TEXT;
    }
}

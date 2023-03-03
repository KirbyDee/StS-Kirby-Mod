package theSorcerer.actions;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class DiscardPileCardChooseAction extends GridCardChooseAction {

    public DiscardPileCardChooseAction(
            int amount
    ) {
        super(AbstractDungeon.player.discardPile, amount);
    }

    public DiscardPileCardChooseAction(
            int amount,
            boolean canCancel
    ) {
        super(AbstractDungeon.player.discardPile, amount, canCancel);
    }

    public DiscardPileCardChooseAction(
            int amount,
            boolean forTransform,
            boolean forUpgrade,
            boolean canCancel,
            boolean forPurge
    ) {
        super(AbstractDungeon.player.discardPile, amount, forTransform, forUpgrade, canCancel, forPurge);
    }
}

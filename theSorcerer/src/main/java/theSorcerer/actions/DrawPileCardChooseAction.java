package theSorcerer.actions;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class DrawPileCardChooseAction extends GridCardChooseAction {

    public DrawPileCardChooseAction(
            int amount
    ) {
        super(AbstractDungeon.player.drawPile, amount);
    }

    public DrawPileCardChooseAction(
            int amount,
            boolean canCancel
    ) {
        super(AbstractDungeon.player.drawPile, amount, canCancel);
    }

    public DrawPileCardChooseAction(
            int amount,
            boolean forTransform,
            boolean forUpgrade,
            boolean canCancel,
            boolean forPurge
    ) {
        super(AbstractDungeon.player.drawPile, amount, forTransform, forUpgrade, canCancel, forPurge);
    }
}

package theSorcerer.actions;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class ExhaustPileCardChooseAction extends GridCardChooseAction {

    public ExhaustPileCardChooseAction(
            int amount
    ) {
        super(AbstractDungeon.player.exhaustPile, amount);
    }

    public ExhaustPileCardChooseAction(
            int amount,
            boolean canCancel
    ) {
        super(AbstractDungeon.player.exhaustPile, amount, canCancel);
    }

    public ExhaustPileCardChooseAction(
            int amount,
            boolean forTransform,
            boolean forUpgrade,
            boolean canCancel,
            boolean forPurge
    ) {
        super(AbstractDungeon.player.exhaustPile, amount, forTransform, forUpgrade, canCancel, forPurge);
    }
}

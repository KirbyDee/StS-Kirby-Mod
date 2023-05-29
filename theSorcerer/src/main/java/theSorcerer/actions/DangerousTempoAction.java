package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;

import java.util.ArrayList;

public class DangerousTempoAction extends AbstractGameAction {

    private final int drawAmount;

    private final int energyAmount;

    private final boolean upgraded;

    public DangerousTempoAction(
            int drawAmount,
            int energyAmount,
            boolean upgraded
    ) {
        this.drawAmount = drawAmount;
        this.energyAmount = energyAmount;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cardsToExhaustInDiscard = new ArrayList<>(AbstractDungeon.player.discardPile.group);
        cardsToExhaustInDiscard.forEach(this::exhaustCardFromDiscardPile);

        DynamicDungeon.drawCard(this.drawAmount);
        DynamicDungeon.gainEnergy(this.energyAmount);
        if (!this.upgraded) {
            DynamicDungeon.applyElementless();
        }

        this.isDone = true;
    }

    private void exhaustCardFromDiscardPile(
            final AbstractCard card
    ) {
        addToTop(
                new ExhaustSpecificCardAction(
                        card,
                        AbstractDungeon.player.discardPile
                )
        );
    }
}

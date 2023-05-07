package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.DynamicDungeon;

public class InsightfulAttackAction extends AbstractGameAction {

    public InsightfulAttackAction(
            int amount
    ) {
        this.amount = amount;
        this.actionType = ActionType.DRAW;
    }

    public void update() {
        DrawCardAction.drawnCards.stream()
                .filter(DynamicDungeon::isElementCard)
                .findAny()
                .ifPresent(this::drawAdditionalCard);

        this.isDone = true;
    }

    private void drawAdditionalCard(final AbstractCard card) {
        card.superFlash();
        DynamicDungeon.drawCard(this.amount);
    }
}

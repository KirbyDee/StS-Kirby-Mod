package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;

import java.util.List;
import java.util.stream.Collectors;

public class MagicSmokeAction extends DrawPileCardChooseAction {

    private final boolean upgraded;

    public MagicSmokeAction(
            int amount,
            boolean upgraded
    ) {
        super(amount);
        this.upgraded = upgraded;
        this.actionType = ActionType.DRAW;
    }

    @Override
    public void update() {
        if (this.upgraded) {
            super.update();
        }
        else {
            moveRandomElementCardsToHand();
            this.isDone = true;
        }
    }

    private void moveRandomElementCardsToHand() {
        List<AbstractCard> possibleCardsToMoveToHand = AbstractDungeon.player.drawPile.group
                .stream()
                .filter(this::canBeChosen)
                .collect(Collectors.toList());
        for (int i = 0; i < this.amount; i++) {
            moveRandomElementCardToHand(possibleCardsToMoveToHand);
        }
    }

    private void moveRandomElementCardToHand(final List<AbstractCard> possibleCardsToMoveToHand) {
        if (possibleCardsToMoveToHand.isEmpty()) {
            return;
        }
        AbstractCard cardToMoveToHand = possibleCardsToMoveToHand.get(AbstractDungeon.cardRandomRng.random(possibleCardsToMoveToHand.size() - 1));
        onCardChosen(cardToMoveToHand);
        possibleCardsToMoveToHand.remove(cardToMoveToHand);
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return DynamicDungeon.isElementCard(card);
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        card.setCostForTurn(0);
        AbstractDungeon.player.drawPile.moveToHand(card);
    }

    @Override
    protected String getChooseText() {
        return "Choose an Element Card to put into your hand"; // TODOO
    }
}

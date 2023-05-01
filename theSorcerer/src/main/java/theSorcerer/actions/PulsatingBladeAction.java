package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PulsatingBladeAction extends HandCardChooseAction {

    // --- VALUES START ---
    private final static String TEXT = "swap their cost."; // TODOO
    // --- VALUES END ---

    public PulsatingBladeAction() {
        super(2);
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        // NOP
    }

    @Override
    protected void onCardsChosen(Stream<AbstractCard> cardStream) {
        this.isDone = true;
        List<AbstractCard> cards = cardStream.collect(Collectors.toList());
        AbstractCard card1 = cards.get(0);
        AbstractCard card2 = cards.get(1);
        int card1CostForTurn = card1.costForTurn;
        card1.setCostForTurn(card2.costForTurn);
        card2.setCostForTurn(card1CostForTurn);

        card1.superFlash();
        card1.applyPowers();
        this.player.hand.addToTop(card1);

        card2.superFlash();
        card2.applyPowers();
        this.player.hand.addToTop(card2);
    }

    @Override
    protected String getChooseText() {
        return TEXT;
    }
}
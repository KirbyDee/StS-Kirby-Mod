package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public abstract class HandCardChooseAction extends CardChooseAction {

    // --- VALUES START ---
    protected final boolean canPickZero;
    protected final boolean forTransform;
    protected final boolean forUpgrade;
    // --- VALUES END ---

    public HandCardChooseAction(
            final boolean canPickZero
    ) {
        this(
                99,
                true,
                canPickZero
        );
    }

    public HandCardChooseAction(
            final int amount
    ) {
        this(
                amount,
                false,
                false
        );
    }

    public HandCardChooseAction(
            final int amount,
            final boolean anyNumber,
            final boolean canPickZero
    ) {
        this(
                amount,
                anyNumber,
                canPickZero,
                false,
                false
        );
    }

    public HandCardChooseAction(
            final int amount,
            final boolean anyNumber,
            final boolean canPickZero,
            final boolean forTransform,
            final boolean forUpgrade
    ) {
        super(AbstractDungeon.player.hand, amount, anyNumber);
        this.canPickZero = canPickZero;
        this.forTransform = forTransform;
        this.forUpgrade = forUpgrade;
    }

    @Override
    protected boolean cardsHaveBeenChosen() {
        return !AbstractDungeon.handCardSelectScreen.wereCardsRetrieved;
    }

    @Override
    protected void prepareCardGroup() {
        this.cardGroup.group.removeAll(this.cannotBeChosen);
    }

    @Override
    protected void showCardSelectionScreen() {
        AbstractDungeon.handCardSelectScreen.open(
                getChooseText(),
                this.amount,
                this.anyNumber,
                this.canPickZero,
                this.forTransform,
                this.forUpgrade
        );
    }

    @Override
    protected ArrayList<AbstractCard> getSelectedCards() {
        return AbstractDungeon.handCardSelectScreen.selectedCards.group;
    }

    protected void addBackToHand(final AbstractCard card) {
        if (!this.playerCouldNotChoose) {
            this.player.hand.addToTop(card);
        }
    }

    @Override
    protected void onActionDone() {
        this.player.hand.refreshHandLayout();
    }

    @Override
    protected void onCardsChosen() {
        super.onCardsChosen();
        afterCardsChosenPerView();
    }

    private void afterCardsChosenPerView() {
        AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        this.cannotBeChosen.forEach(this.player.hand::addToTop);
    }
}

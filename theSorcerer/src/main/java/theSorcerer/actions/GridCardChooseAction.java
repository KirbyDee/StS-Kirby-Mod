package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public abstract class GridCardChooseAction extends CardChooseAction {


    // --- VALUES START ---
    private final boolean forTransform;
    private final boolean forUpgrade;
    private final boolean canCancel;
    private final boolean forPurge;
    private CardGroup tmp;
    // --- VALUES END ---


    public GridCardChooseAction(
            final CardGroup cardGroup,
            final int amount
    ) {
        this(
                cardGroup,
                amount,
                false
        );
    }

    public GridCardChooseAction(
            final CardGroup cardGroup,
            final int amount,
            final boolean canCancel
    ) {
        this(
                cardGroup,
                amount,
                false,
                false,
                canCancel,
                false
        );
    }

    public GridCardChooseAction(
            final CardGroup cardGroup,
            final int amount,
            final boolean forTransform,
            final boolean forUpgrade,
            final boolean canCancel,
            final boolean forPurge
    ) {
        super(cardGroup, amount);
        this.forTransform = forTransform;
        this.forUpgrade = forUpgrade;
        this.canCancel = canCancel;
        this.forPurge = forPurge;
    }

    @Override
    protected boolean cardsHaveBeenChosen() {
        return AbstractDungeon.gridSelectScreen.selectedCards.size() != 0;
    }

    @Override
    protected void prepareCardGroup() {
        this.tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        this.cardGroup.group
                .stream()
                .filter(this::canBeChosen)
                .forEach(this.tmp::addToRandomSpot);
    }

    @Override
    protected void showCardSelectionScreen() {
        AbstractDungeon.gridSelectScreen.open(
                this.tmp,
                this.amount,
                getChooseText(),
                this.forUpgrade,
                this.forTransform,
                this.canCancel,
                this.forPurge
        );
    }

    @Override
    protected ArrayList<AbstractCard> getSelectedCards() {
        return AbstractDungeon.gridSelectScreen.selectedCards;
    }

    @Override
    protected void onActionDone() {
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        this.player.hand.refreshHandLayout();
    }
}

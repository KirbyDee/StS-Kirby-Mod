package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class CardChooseAction extends AbstractGameAction {

    private static final Logger LOG = LogManager.getLogger(CardChooseAction.class.getName());

    // --- VALUES START ---
    protected final AbstractPlayer player;
    protected final ArrayList<AbstractCard> cannotBeChosen = new ArrayList<>();
    protected final CardGroup cardGroup;
    // --- VALUES END ---

    public CardChooseAction(
            final CardGroup cardGroup,
            final int amount
    ) {
        this.amount = amount;
        this.cardGroup = cardGroup;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.player = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        // init: either no card can be chosen, only 1 or the player chooses one
        if (this.duration == Settings.ACTION_DUR_FAST) {
            // get all the cards which cannot be chosen
            gatherCannotBeChosen();

            // if no cards are left -> nothing to do
            if (this.cannotBeChosen.size() == this.cardGroup.group.size()) {
                LOG.info("No cards to choose from -> nop");
                this.isDone = true;
                return;
            }

            // if we less or equal this.amount cards, take them directly without choosing
            if (this.cardGroup.group.size() - this.cannotBeChosen.size() <= this.amount) {
                choseOnlyCardsPossible();
                return;
            }

            prepareCardGroup();
            showCardSelectionScreen();
            tickDuration();
            return;
        }

        // card has been chosen
        if (cardsHaveBeenChosen()) {
            onCardsChosen();
        }

        // tick -> go into next step
        tickDuration();
    }

    private boolean cannotBeChosen(final AbstractCard card) {
        return !canBeChosen(card);
    }

    protected boolean canBeChosen(final AbstractCard card) {
        return (card.type != AbstractCard.CardType.STATUS && card.type != AbstractCard.CardType.CURSE) ||
                (curseCanBeChosen() && card.type == AbstractCard.CardType.CURSE) ||
                (statusCanBeChosen() && card.type == AbstractCard.CardType.STATUS);
    }

    private void gatherCannotBeChosen() {
        this.cardGroup.group
                .stream()
                .filter(this::cannotBeChosen)
                .forEach(this.cannotBeChosen::add);
    }

    private void choseOnlyCardsPossible() {
        List<AbstractCard> cards = this.cardGroup.group
                .stream()
                .filter(this::canBeChosen)
                .limit(this.amount)
                .collect(Collectors.toList());
        onCardsChosen(cards.stream());
    }

    private void onCardsChosen() {
        onCardsChosen(getSelectedCards().stream());
        onActionDone();
    }

    private void onCardsChosen(Stream<AbstractCard> cards) {
        this.isDone = true;
        cards
                .peek(c -> LOG.debug("Card chosen: " + c.name))
                .forEach(this::onCardChosen);
    }

    protected boolean curseCanBeChosen() {
        return false;
    }

    protected boolean statusCanBeChosen() {
        return false;
    }

    protected abstract void prepareCardGroup();

    protected abstract boolean cardsHaveBeenChosen();

    protected abstract void onCardChosen(final AbstractCard card);

    protected abstract void showCardSelectionScreen();

    protected abstract String getChooseText();

    protected abstract ArrayList<AbstractCard> getSelectedCards();

    protected void onActionDone() {}
}

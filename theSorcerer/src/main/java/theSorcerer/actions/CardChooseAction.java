package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public abstract class CardChooseAction extends AbstractGameAction {

    private static final Logger LOG = LogManager.getLogger(CardChooseAction.class.getName());

    // --- VALUES START ---
    private final AbstractPlayer player;
    private final ArrayList<AbstractCard> cannotBeChosen = new ArrayList<>();
    private final int cardAmount;
    private final boolean canPickZero;
    private final boolean forTransform;
    private final boolean forUpgrade;
    // --- VALUES END ---

    public CardChooseAction(
            final int cardAmount
    ) {
        this(cardAmount, false, false, false);
    }

    public CardChooseAction(
            final int cardAmount,
            final boolean canPickZero,
            final boolean forTransform,
            final boolean forUpgrade
    ) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.player = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.cardAmount = cardAmount;
        this.canPickZero = canPickZero;
        this.forTransform = forTransform;
        this.forUpgrade = forUpgrade;
    }

    public void update() {
        // init: either no card can be chosen, only 1 or the player chooses one
        if (this.duration == Settings.ACTION_DUR_FAST) {
            // get all the cards in hand which cannot be chosen
            gatherCannotBeChosen();

            // if no cards are left -> nothing to do
            if (this.cannotBeChosen.size() == this.player.hand.group.size()) {
                this.isDone = true;
                return;
            }

            // if we have exactly 1 card that can be chosen
            if (this.player.hand.group.size() - this.cannotBeChosen.size() == 1) {
                choseOnlyCardInHand();
                return;
            }

            showCardSelectionScreen();
            return;
        }

        // card has been chosen
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
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

    protected boolean curseCanBeChosen() {
        return false;
    }

    protected boolean statusCanBeChosen() {
        return false;
    }

    protected abstract void onCardChosen(final AbstractCard card);

    private void gatherCannotBeChosen() {
        this.player.hand.group
                .stream()
                .filter(this::cannotBeChosen)
                .forEach(this.cannotBeChosen::add);
    }

    private void choseOnlyCardInHand() {
        this.player.hand.group
                .stream()
                .filter(this::canBeChosen)
                .findAny()
                .ifPresent(c -> {
                    LOG.debug("Card chosen: " + c.name);
                    onCardChosen(c);
                    this.isDone = true;
                });
    }

    private void showCardSelectionScreen() {
        this.player.hand.group.removeAll(this.cannotBeChosen);
        AbstractDungeon.handCardSelectScreen.open(getChooseText(), this.cardAmount, false, this.canPickZero, this.forTransform, this.forUpgrade);
        tickDuration();
    }

    protected abstract String getChooseText();

    private void onCardsChosen() {
        AbstractDungeon.handCardSelectScreen.selectedCards.group
                .forEach(c -> {
                    LOG.debug("Card chosen: " + c.name);
                    onCardChosen(c);
                    this.player.hand.addToTop(c);
                });

        returnCards();
        AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        this.isDone = true;
    }

    private void returnCards() {
        this.cannotBeChosen.forEach(this.player.hand::addToTop);
        this.player.hand.refreshHandLayout();
    }
}

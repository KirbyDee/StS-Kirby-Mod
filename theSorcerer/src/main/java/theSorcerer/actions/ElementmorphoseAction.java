package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.cards.DynamicCard;

import java.util.ArrayList;

public abstract class ElementmorphoseAction extends AbstractGameAction {

    // --- VALUES START ---
    private final AbstractPlayer player;
    private final ArrayList<AbstractCard> cannotBecomeElement = new ArrayList<>();
    private final AbstractCard.CardTags elementToMetamorph;
    private final String elementToMetamorphPrefix;
    private final AbstractCard.CardTags oppositeElement;
    private final String oppositeElementPrefix;
    private final boolean upgraded;
    // --- VALUES END ---

    public ElementmorphoseAction(
            final AbstractCard.CardTags elementToMetamorph,
            final String elementToMetamorphPrefix,
            final AbstractCard.CardTags oppositeElement,
            final String oppositeElementPrefix,
            final boolean upgraded
    ) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.player = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
        this.elementToMetamorph = elementToMetamorph;
        this.elementToMetamorphPrefix = elementToMetamorphPrefix;
        this.oppositeElement = oppositeElement;
        this.oppositeElementPrefix = oppositeElementPrefix;
        this.upgraded = upgraded;
    }

    public void update() {
        // init: either no card can be metamorphed, only 1 or the player chooses one
        if (this.duration == Settings.ACTION_DUR_FAST) {
            // get all the cards in hand which cannot be metamorphed to the desired element
            gatherCannotBecomeElement();

            // if no cards are left -> nothing to do
            if (this.cannotBecomeElement.size() == this.player.hand.group.size()) {
                this.isDone = true;
                return;
            }

            // if we have exactly 1 card that can be metamorphed, metamorph it
            if (this.player.hand.group.size() - this.cannotBecomeElement.size() == 1) {
                metamorphOnlyCardInHand();
                return;
            }

            showCardSelectionScreen();
            return;
        }

        // card has been chosen
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            metamorphOnlyChosenCards();
        }

        // tick -> go into next step
        tickDuration();
    }

    private boolean cannotBeMetamorphed(final AbstractCard card) {
        return !canBeMetamorphed(card);
    }

    private boolean canBeMetamorphed(final AbstractCard card) {
        return !card.hasTag(this.elementToMetamorph) &&
                card.type != AbstractCard.CardType.CURSE &&
                card.type != AbstractCard.CardType.STATUS;
    }

    private void metamorph(final AbstractCard card) {
        card.tags.add(this.elementToMetamorph);
        if (card.hasTag(this.oppositeElement)) {
            card.tags.remove(this.oppositeElement);
            card.rawDescription = card.rawDescription.replace(this.oppositeElementPrefix, this.elementToMetamorphPrefix);
        }
        else {
            card.rawDescription = DynamicCard.MOD_PREFIX + this.elementToMetamorphPrefix + DynamicCard.NEW_LINE + card.rawDescription;
        }
        card.superFlash();
        card.applyPowers();
        card.initializeDescription();
    }

    private void gatherCannotBecomeElement() {
        this.player.hand.group
                .stream()
                .filter(this::cannotBeMetamorphed)
                .forEach(this.cannotBecomeElement::add);
    }

    private void metamorphOnlyCardInHand() {
        this.player.hand.group
                .stream()
                .filter(this::canBeMetamorphed)
                .findAny()
                .ifPresent(c -> {
                    metamorph(c);
                    this.isDone = true;
                });
    }

    private void showCardSelectionScreen() {
        this.player.hand.group.removeAll(this.cannotBecomeElement);
        AbstractDungeon.handCardSelectScreen.open("HELLO!!", 1, false, false); // TODO
        tickDuration();
    }

    private void metamorphOnlyChosenCards() {
        AbstractDungeon.handCardSelectScreen.selectedCards.group
                .forEach(c -> {
                    metamorph(c);
                    this.player.hand.addToTop(c);
                });

        returnCards();
        AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        this.isDone = true;
    }

    private void returnCards() {
        this.cannotBecomeElement.forEach(this.player.hand::addToTop);
        this.player.hand.refreshHandLayout();
    }
}

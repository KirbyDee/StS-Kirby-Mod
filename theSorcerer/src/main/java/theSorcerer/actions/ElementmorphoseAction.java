package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.apache.commons.lang3.StringUtils;
import theSorcerer.cards.DynamicCard;

public abstract class ElementmorphoseAction extends CardChooseAction {

    // --- VALUES START ---
    private final static String TEXT_1 = "metamorphose to ";
    private final static String TEXT_2 = " for the rest of the Combat";
    private final static int CARDS_TO_CHOOSE = 1;
    private final AbstractCard.CardTags elementToMetamorph;
    private final DynamicCard.CardAbility elementToMetamorphPrefix;
    private final AbstractCard.CardTags oppositeElement;
    private final DynamicCard.CardAbility oppositeElementPrefix;
    // --- VALUES END ---

    public ElementmorphoseAction(
            final AbstractCard.CardTags elementToMetamorph,
            final DynamicCard.CardAbility elementToMetamorphPrefix,
            final AbstractCard.CardTags oppositeElement,
            final DynamicCard.CardAbility oppositeElementPrefix
    ) {
        super(CARDS_TO_CHOOSE);
        this.elementToMetamorph = elementToMetamorph;
        this.elementToMetamorphPrefix = elementToMetamorphPrefix;
        this.oppositeElement = oppositeElement;
        this.oppositeElementPrefix = oppositeElementPrefix;
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return !card.hasTag(this.elementToMetamorph) &&
                card.costForTurn >= 0 &&
                super.canBeChosen(card);
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        card.tags.add(this.elementToMetamorph);
        if (card.hasTag(this.oppositeElement)) {
            card.tags.remove(this.oppositeElement);
            card.rawDescription = card.rawDescription.replace(this.oppositeElementPrefix.text, this.elementToMetamorphPrefix.text);
        }
        else {
            card.rawDescription = this.elementToMetamorphPrefix.text + DynamicCard.NEW_LINE + card.rawDescription;
        }
        card.superFlash();
        card.applyPowers();
        card.initializeDescription();
    }

    @Override
    protected String getChooseText() {
        return TEXT_1 + StringUtils.capitalize(elementToMetamorphPrefix.name().toLowerCase()) + TEXT_2;
    }
}

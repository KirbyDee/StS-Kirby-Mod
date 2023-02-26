package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.apache.commons.lang3.StringUtils;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;

public abstract class ElementmorphoseAction extends CardChooseAction {

    // --- VALUES START ---
    private final static String TEXT_1 = "metamorphose to "; // TODO
    private final static String TEXT_2 = " for the rest of the Combat.";
    private final static int CARDS_TO_CHOOSE = 1;
    private final AbstractCard.CardTags elementToMetamorph;
    private final CardAbility elementToMetamorphPrefix;
    private final AbstractCard.CardTags oppositeElement;
    // --- VALUES END ---

    public ElementmorphoseAction(
            final AbstractCard.CardTags elementToMetamorph,
            final CardAbility elementToMetamorphPrefix,
            final AbstractCard.CardTags oppositeElement
    ) {
        super(CARDS_TO_CHOOSE);
        this.elementToMetamorph = elementToMetamorph;
        this.elementToMetamorphPrefix = elementToMetamorphPrefix;
        this.oppositeElement = oppositeElement;
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return !card.hasTag(this.elementToMetamorph) &&
                card.costForTurn >= 0 &&
                super.canBeChosen(card);
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        // add/remove element to/from tags & abilities
        card.tags.add(this.elementToMetamorph);
        card.tags.remove(this.oppositeElement);
        AbstractCardPatch.abilities.get(card).add(CardAbility.from(this.elementToMetamorph));
        AbstractCardPatch.abilities.get(card).remove(CardAbility.from(this.oppositeElement));

        // apply flash, powers and init shown description
        card.superFlash();
        card.applyPowers();
        card.initializeDescription();

        // add back to hand
        this.player.hand.addToTop(card);
    }

    @Override
    protected String getChooseText() {
        return TEXT_1 + StringUtils.capitalize(elementToMetamorphPrefix.name().toLowerCase()) + TEXT_2;
    }
}

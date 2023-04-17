package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import org.apache.commons.lang3.StringUtils;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.patches.cards.CardAbility;

import java.util.function.Consumer;

public abstract class ElementmorphoseAction extends HandCardChooseAction {

    // --- VALUES START ---
    private final static String TEXT_1 = "metamorphose to "; // TODO
    private final static String TEXT_2 = " for the rest of the Combat.";
    private final static int CARDS_TO_CHOOSE = 1;
    private final CardAbility elementToMetamorph;
    private final Consumer<AbstractCard> applyElementToCard;
    // --- VALUES END ---

    public ElementmorphoseAction(
            final CardAbility elementToMetamorph,
            final Consumer<AbstractCard> applyElementToCard
    ) {
        // TODO: upgrade is wrong.. but would be nice to have a nice screen to show what is different (check HanCardSelectScreen)
        super(CARDS_TO_CHOOSE, false, false, false, true);
        this.elementToMetamorph = elementToMetamorph;
        this.applyElementToCard = applyElementToCard;
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return !DynamicDungeon.cardHasAbility(card, this.elementToMetamorph) &&
                !DynamicDungeon.isArcaneCard(card) &&
                card.costForTurn >= 0 &&
                super.canBeChosen(card);
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        // add/remove element to/from tags & abilities
        this.applyElementToCard.accept(card);

        // apply flash, powers and init shown description
        card.superFlash();
        card.applyPowers();
        card.initializeDescription();

        // add back to hand
        this.player.hand.addToTop(card);
    }

    @Override
    protected String getChooseText() {
        return TEXT_1 + StringUtils.capitalize(elementToMetamorph.name().toLowerCase()) + TEXT_2;
    }
}

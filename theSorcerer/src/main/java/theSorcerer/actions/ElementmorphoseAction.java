package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import org.apache.commons.lang3.StringUtils;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;
import theSorcerer.patches.screens.select.HandCardSelectScreenPatch;

import java.util.function.Consumer;

public abstract class ElementmorphoseAction extends HandCardChooseAction {

    // --- VALUES START ---
    private final static String TEXT_1 = "metamorphose to "; // TODOO
    private final static String TEXT_2 = " for the rest of the Combat.";
    private final static int CARDS_TO_CHOOSE = 1;
    private final CardModifier elementToMetamorph;
    private final Consumer<AbstractCard> applyElementToCard;
    private final boolean upgraded;
    // --- VALUES END ---

    public ElementmorphoseAction(
            CardModifier elementToMetamorph,
            Consumer<AbstractCard> applyElementToCard,
            boolean upgraded
    ) {
        super(CARDS_TO_CHOOSE, false, false, false, false);
        this.elementToMetamorph = elementToMetamorph;
        this.applyElementToCard = applyElementToCard;
        this.upgraded = upgraded;
        this.actionType = ActionType.CARD_MANIPULATION;
    }


    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST && this.upgraded) {
            this.cardGroup.group
                    .stream()
                    .filter(this::canBeChosen)
                    .forEach(this::onCardChosen);

            this.isDone = true;
            return;
        }
        super.update();
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return !DynamicDungeon.cardHasModifier(card, this.elementToMetamorph) &&
                !DynamicDungeon.isArcaneCard(card) &&
                card.costForTurn >= -1 &&
                super.canBeChosen(card);
    }

    @Override
    protected void showCardSelectionScreen() {
        HandCardSelectScreenPatch.open(
                getChooseText(),
                this.amount,
                this.anyNumber,
                this.canPickZero,
                this.forTransform,
                this.forUpgrade,
                this.applyElementToCard
        );
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        // add/remove element to/from tags & abilities
        this.applyElementToCard.accept(card);

        // apply flash, powers
        card.superFlash();
        card.applyPowers();

        // add back to hand
        if (!this.upgraded) {
            addBackToHand(card);
        }
    }

    @Override
    protected String getChooseText() {
        return TEXT_1 + StringUtils.capitalize(elementToMetamorph.name().toLowerCase()) + TEXT_2;
    }
}

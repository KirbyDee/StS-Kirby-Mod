package theSorcerer.cards.fire;

import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCard;
import theSorcerer.modifiers.CardModifier;

public abstract class SorcererFireCard extends SorcererCard {

    public SorcererFireCard(
            DynamicCard.InfoBuilder infoBuilder
    ) {
        super(
                infoBuilder.modifiers(CardModifier.FIRE)
        );
    }
}

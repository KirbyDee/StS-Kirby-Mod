package theSorcerer.cards.ice;

import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCard;
import theSorcerer.modifiers.CardModifier;

public abstract class SorcererIceCard extends SorcererCard {

    public SorcererIceCard(
            DynamicCard.InfoBuilder infoBuilder
    ) {
        super(
                infoBuilder.modifiers(CardModifier.ICE)
        );
    }
}

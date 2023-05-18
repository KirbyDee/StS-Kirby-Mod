package theSorcerer.cards.arcane;

import theSorcerer.cards.SorcererCard;
import theSorcerer.modifiers.CardModifier;

public abstract class SorcererArcaneCard extends SorcererCard {

    public SorcererArcaneCard(
            InfoBuilder infoBuilder
    ) {
        super(
                infoBuilder.modifiers(CardModifier.ARCANE)
        );
    }
}

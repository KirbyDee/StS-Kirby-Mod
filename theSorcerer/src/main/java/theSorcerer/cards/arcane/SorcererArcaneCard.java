package theSorcerer.cards.arcane;

import theSorcerer.cards.SorcererCard;
import theSorcerer.cards.SorcererCardTags;

public abstract class SorcererArcaneCard extends SorcererCard {

    public SorcererArcaneCard(
            InfoBuilder infoBuilder
    ) {
        super(
                infoBuilder.tags(SorcererCardTags.ARCANE)
        );
    }
}

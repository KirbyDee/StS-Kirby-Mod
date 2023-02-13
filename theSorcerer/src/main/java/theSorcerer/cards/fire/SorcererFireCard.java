package theSorcerer.cards.fire;

import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCard;
import theSorcerer.cards.SorcererCardTags;

public abstract class SorcererFireCard extends SorcererCard {

    public SorcererFireCard(
            DynamicCard.InfoBuilder infoBuilder
    ) {
        super(
                infoBuilder.tags(SorcererCardTags.FIRE)
        );
    }
}

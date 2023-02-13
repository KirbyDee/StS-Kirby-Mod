package theSorcerer.cards.ice;

import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCard;
import theSorcerer.cards.SorcererCardTags;

public abstract class SorcererIceCard extends SorcererCard {

    public SorcererIceCard(
            DynamicCard.InfoBuilder infoBuilder
    ) {
        super(
                infoBuilder.tags(SorcererCardTags.ICE)
        );
    }
}

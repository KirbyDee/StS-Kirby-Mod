package theSorcerer.cards.ice;

import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCard;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.patches.cards.CardAbility;

public abstract class SorcererIceCard extends SorcererCard {

    public SorcererIceCard(
            DynamicCard.InfoBuilder infoBuilder
    ) {
        super(
                infoBuilder.abilities(CardAbility.ICE)
        );
    }
}

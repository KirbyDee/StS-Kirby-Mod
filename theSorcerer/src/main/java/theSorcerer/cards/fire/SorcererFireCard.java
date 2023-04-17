package theSorcerer.cards.fire;

import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCard;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.patches.cards.CardAbility;

public abstract class SorcererFireCard extends SorcererCard {

    public SorcererFireCard(
            DynamicCard.InfoBuilder infoBuilder
    ) {
        super(
                infoBuilder.abilities(CardAbility.FIRE)
        );
    }
}

package theSorcerer.cards.arcane;

import theSorcerer.cards.SorcererCard;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.patches.cards.CardAbility;

public abstract class SorcererArcaneCard extends SorcererCard {

    public SorcererArcaneCard(
            InfoBuilder infoBuilder
    ) {
        super(
                infoBuilder.abilities(CardAbility.ARCANE)
        );
    }
}

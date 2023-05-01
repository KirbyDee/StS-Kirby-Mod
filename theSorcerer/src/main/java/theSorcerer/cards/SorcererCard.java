package theSorcerer.cards;

import theSorcerer.characters.TheSorcerer;

public abstract class SorcererCard extends DynamicCard {

    public SorcererCard(
           DynamicCard.InfoBuilder infoBuilder
    ) {
        super(
                infoBuilder
                        .color(TheSorcerer.Enums.COLOR_YELLOW)
                        .build()
        );
    }
}

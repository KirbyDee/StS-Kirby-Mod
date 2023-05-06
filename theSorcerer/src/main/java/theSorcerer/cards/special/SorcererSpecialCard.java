package theSorcerer.cards.special;

import theSorcerer.cards.SorcererCard;

public abstract class SorcererSpecialCard extends SorcererCard {

    public SorcererSpecialCard(
            InfoBuilder infoBuilder
    ) {
        super(
                infoBuilder.rarity(CardRarity.SPECIAL)
        );
    }
}

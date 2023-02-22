package theSorcerer.cards;

import theSorcerer.characters.TheSorcerer;

public abstract class SorcererCard extends DynamicCard {

    public SorcererCard(
           DynamicCard.InfoBuilder infoBuilder
    ) {
        super(
                infoBuilder
                        .color(TheSorcerer.Enums.COLOR_ORANGE)
                        .build()
        );
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeValues();
            initializeDescription();
        }
    }

    protected abstract void upgradeValues();
}

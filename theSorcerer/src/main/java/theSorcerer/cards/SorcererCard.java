package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return !this.unplayable && super.canUse(p, m);
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

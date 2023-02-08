package theSorcerer.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import theSorcerer.KirbyDeeMod;
import theSorcerer.characters.TheSorcerer;

import static theSorcerer.KirbyDeeMod.makeCardPath;

public abstract class SorcererCard extends CustomCard {

    public static String getId(Class<? extends SorcererCard> thisClazz) {
        return KirbyDeeMod.makeID(thisClazz.getSimpleName());
    }

    private static CardStrings getCardStrings(Class<? extends SorcererCard> thisClazz) {
        return CardCrawlGame.languagePack.getCardStrings(getId(thisClazz));
    }

    public SorcererCard(
            Class<? extends SorcererCard> thisClazz,
            int cost,
            CardType cardType,
            CardRarity rarity,
            CardTarget target
    ) {
        super(
                getId(thisClazz),
                getCardStrings(thisClazz).NAME,
                makeCardPath(cardType, getCardStrings(thisClazz).NAME),
                cost,
                getCardStrings(thisClazz).DESCRIPTION,
                cardType,
                TheSorcerer.Enums.COLOR_ORANGE,
                rarity,
                target
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

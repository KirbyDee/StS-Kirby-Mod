package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theSorcerer.cards.SorcererCard;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.cards.SorcererElementCard;
import theSorcerer.powers.IceAffinityPower;

public abstract class SorcererIceCard extends SorcererElementCard<IceAffinityPower> {

    public SorcererIceCard(
            Class<? extends SorcererCard> thisClazz,
            int cost,
            CardType cardType,
            CardRarity rarity,
            CardTarget target
    ) {
        super(
                thisClazz,
                cost,
                cardType,
                rarity,
                target
        );
    }

    @Override
    protected CardTags getElement() {
        return SorcererCardTags.ICE;
    }

    @Override
    protected IceAffinityPower getAffinityPower(AbstractPlayer p, int amount) {
        return new IceAffinityPower(p, amount);
    }
}

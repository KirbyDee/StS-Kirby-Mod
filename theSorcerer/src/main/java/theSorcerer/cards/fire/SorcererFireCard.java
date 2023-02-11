package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theSorcerer.cards.SorcererCard;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.cards.SorcererElementCard;
import theSorcerer.powers.buff.FireAffinityPower;

public abstract class SorcererFireCard extends SorcererElementCard<FireAffinityPower> {

    public SorcererFireCard(
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
        return SorcererCardTags.FIRE;
    }

    @Override
    protected FireAffinityPower getAffinityPower(AbstractPlayer p, int amount) {
        return new FireAffinityPower(p, amount);
    }
}

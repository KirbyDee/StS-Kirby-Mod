package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.powers.AffinityPower;

public abstract class SorcererElementCard<A extends AffinityPower> extends SorcererCard {

    public SorcererElementCard(
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

        this.tags.add(getElement());
    }

    protected abstract AbstractCard.CardTags getElement();

    @Override
    public final void use(AbstractPlayer p, AbstractMonster m) {
        increaseElementAffinity(p);
        onCardUse(p, m);
    }

    protected abstract void onCardUse(AbstractPlayer p, AbstractMonster m);

    private void increaseElementAffinity(AbstractPlayer p) {
        addToBot(
                new ApplyPowerAction(
                        p,
                        p,
                        getAffinityPower(p, this.cost),
                        this.cost
                )
        );
    }

    protected abstract A getAffinityPower(AbstractPlayer p, int amount);
}

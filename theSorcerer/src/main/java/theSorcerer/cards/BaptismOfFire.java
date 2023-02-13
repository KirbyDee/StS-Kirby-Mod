package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.BaptismOfFireAction;

public class BaptismOfFire extends DynamicCard {

    public BaptismOfFire() {
        super(

                DynamicCard.InfoBuilder(BaptismOfFire.class)
                        .type(CardType.POWER)
                        .rarity(CardRarity.SPECIAL)
                        .build()
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        addToBot(new BaptismOfFireAction(this.upgraded));
    }
}

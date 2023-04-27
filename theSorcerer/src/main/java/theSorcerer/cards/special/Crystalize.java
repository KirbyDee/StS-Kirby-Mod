package theSorcerer.cards.special;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.CrystalizeAction;
import theSorcerer.cards.DynamicCard;

public class Crystalize extends DynamicCard {

    public Crystalize() {
        super(

                DynamicCard.InfoBuilder(Crystalize.class)
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
        addToBot(new CrystalizeAction());
    }
}

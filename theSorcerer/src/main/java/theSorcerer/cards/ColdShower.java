package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.ColdShowerAction;

public class ColdShower extends DynamicCard {

    public ColdShower() {
        super(

                DynamicCard.InfoBuilder(ColdShower.class)
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
        addToBot(new ColdShowerAction());
    }
}

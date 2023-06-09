package theSorcerer.cards.special;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.BaptismOfFireAction;
import theSorcerer.cards.DynamicCard;

public class BaptismOfFire extends SorcererSpecialCard {

    public BaptismOfFire() {
        super(
                DynamicCard.InfoBuilder(BaptismOfFire.class)
                        .type(CardType.POWER)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        addToBot(new BaptismOfFireAction(this.upgraded));
    }
}

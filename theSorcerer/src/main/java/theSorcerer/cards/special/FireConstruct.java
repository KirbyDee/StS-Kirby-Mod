package theSorcerer.cards.special;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.fire.FireStrike;

public class FireConstruct extends SorcererSpecialCard {

    public FireConstruct() {
        super(
                DynamicCard.InfoBuilder(FireConstruct.class)
                        .type(CardType.POWER)
        );

        this.cardsToPreview = new FireStrike();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        addToBot(new MakeTempCardInHandAction(new FireStrike(), 1, false));
    }
}

package theSorcerer.cards.special;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.ice.IceDefend;

public class IceConstruct extends SorcererSpecialCard {

    public IceConstruct() {
        super(
                DynamicCard.InfoBuilder(IceConstruct.class)
                        .type(CardType.POWER)
        );

        this.cardsToPreview = new IceDefend();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        addToBot(new MakeTempCardInHandAction(new IceDefend(), 1, false));
    }
}

package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.ice.FrostArmor;

public class IceConstruct extends DynamicCard {

    public IceConstruct() {
        super(
                DynamicCard.InfoBuilder(IceConstruct.class)
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
        addToBot(new MakeTempCardInHandAction(new FrostArmor(), 1, false));
    }
}

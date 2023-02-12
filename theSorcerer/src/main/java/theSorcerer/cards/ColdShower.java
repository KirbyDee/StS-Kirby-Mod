package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.ColdShowerAction;

public class ColdShower extends DynamicCard {

    // --- VALUES START ---
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardType CARD_TYPE = CardType.POWER;
    // --- VALUES END ---

    public ColdShower() {
        super(

                DynamicCard.InfoBuilder(ColdShower.class)
                        .type(CARD_TYPE)
                        .rarity(RARITY)
                        .build()
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        onChoseThisOption();
    }

    @Override
    public void onChoseThisOption() {
        addToBot(new ColdShowerAction(this.upgraded));
    }
}

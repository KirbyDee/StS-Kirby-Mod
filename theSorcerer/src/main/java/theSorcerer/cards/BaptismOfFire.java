package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.BaptismOfFireAction;

public class BaptismOfFire extends DynamicCard {

    // --- VALUES START ---
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardType CARD_TYPE = CardType.POWER;
    // --- VALUES END ---

    public BaptismOfFire() {
        super(

                DynamicCard.InfoBuilder(BaptismOfFire.class)
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
        addToBot(new BaptismOfFireAction(this.upgraded));
    }
}

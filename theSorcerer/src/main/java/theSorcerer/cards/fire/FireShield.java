package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;

public class FireShield extends SorcererFireCard { // TODO: rename and different card

    // --- VALUES START ---
    private static final int COST = 0;
    private static final int BLOCK = 3;
    // --- VALUES END ---

    public FireShield() {
        super(
                DynamicCard.InfoBuilder(FireShield.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.SELF)
                        .block(BLOCK)
        );

        this.cardsToPreview = new Burn();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(
                new GainBlockAction(
                        p,
                        p,
                        this.block
                )
        );

        addToBot(
                new MakeTempCardInDiscardAction(new Burn(), 1)
        );
    }
}

package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import theSorcerer.modifiers.CardModifier;

public class WellPrepared extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 0;
    private static final int CARD_AMOUNT_DRAW = 2;
    // --- VALUES END ---

    public WellPrepared() {
        super(
                DynamicCard.InfoBuilder(WellPrepared.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.RARE)
                        .modifiers(
                                CardModifier.FUTURITY,
                                CardModifier.FLASHBACK
                        )
                        .magicNumber(CARD_AMOUNT_DRAW)
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));

        if (!this.upgraded) {
            addToBot(new ApplyPowerAction(p, p, new NoDrawPower(p)));
        }
    }
}

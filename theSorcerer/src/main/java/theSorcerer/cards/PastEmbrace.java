package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DarkEmbracePower;
import theSorcerer.powers.buff.PastEmbracePower;

public class PastEmbrace extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    private static final int CARD_DRAW = 1;
    // --- VALUES END ---

    public PastEmbrace() {
        super(
                DynamicCard.InfoBuilder(PastEmbrace.class)
                        .cost(COST)
                        .type(CardType.POWER)
                        .rarity(CardRarity.UNCOMMON)
                        .magicNumber(CARD_DRAW)
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(
                new ApplyPowerAction(p, p, new PastEmbracePower(p, this.magicNumber), this.magicNumber)
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeBaseCost(UPGRADE_COST);
    }
}

package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.powers.buff.PastEmbracePower;

public class PastEmbrace extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int DRAW_CARD = 1;
    private static final int UPGRADE_DRAW_CARD = 1;
    // --- VALUES END ---

    public PastEmbrace() {
        super(
                DynamicCard.InfoBuilder(PastEmbrace.class)
                        .cost(COST)
                        .type(CardType.POWER)
                        .rarity(CardRarity.UNCOMMON)
                        .magicNumber(DRAW_CARD)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ApplyPowerAction(
                        player,
                        player,
                        new PastEmbracePower(player, this.magicNumber),
                        this.magicNumber
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_DRAW_CARD);
    }
}

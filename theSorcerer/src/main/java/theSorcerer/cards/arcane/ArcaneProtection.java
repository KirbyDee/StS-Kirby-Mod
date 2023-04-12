package theSorcerer.cards.arcane;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;

public class ArcaneProtection extends SorcererArcaneCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int BLOCK = 2;
    private static final int UPGRADE_BLOCK = 1;
    private static final int BLOCK_TIMES = 4;
    // --- VALUES END ---

    public ArcaneProtection() {
        super(
                DynamicCard.InfoBuilder(ArcaneProtection.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .block(BLOCK)
                        .magicNumber(BLOCK_TIMES)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        for (int i = 0; i < this.magicNumber; ++i) {
            addToBot(
                    new GainBlockAction(
                            player,
                            player,
                            this.block
                    )
            );
        }
    }

    @Override
    protected void upgradeValues() {
        upgradeBlock(UPGRADE_BLOCK);
    }
}

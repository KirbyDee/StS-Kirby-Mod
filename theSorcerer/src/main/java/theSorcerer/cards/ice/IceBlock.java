package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import theSorcerer.cards.DynamicCard;

public class IceBlock extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int BLOCK_PRIMARY = 10;
    private static final int UPGRADE_BLOCK_PRIMARY = 2;
    private static final int BLOCK_SECONDARY = 5;
    private static final int UPGRADE_BLOCK_SECONDARY = 1;
    // --- VALUES END ---

    public IceBlock() {
        super(
                DynamicCard.InfoBuilder(IceBlock.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.SELF)
                        .block(BLOCK_PRIMARY)
                        .magicNumber(BLOCK_SECONDARY)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // block this turn
        addToBot(
                new GainBlockAction(
                        player,
                        player,
                        this.block
                )
        );

        // block next turn
        addToBot(
                new ApplyPowerAction(
                        player,
                        player,
                        new NextTurnBlockPower(
                                player,
                                this.magicNumber
                        ),
                        this.magicNumber
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeBlock(UPGRADE_BLOCK_PRIMARY);
        upgradeMagicNumber(UPGRADE_BLOCK_SECONDARY);
    }
}

package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.DiscardedDefenseAction;

public class DiscardedDefense extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int CARD_DRAW = 1;
    private static final int CARDS_DISCARDED = 0;
    // --- VALUES END ---

    public DiscardedDefense() {
        super(
                DynamicCard.InfoBuilder(DiscardedDefense.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.SELF)
                        .magicNumber(CARD_DRAW)
                        .secondMagicNumber(CARDS_DISCARDED)
                        .block(BLOCK)
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // draw a card
        addToBot(new DrawCardAction(this.magicNumber));

        // discard as many cards as needed and get block
        addToBot(new DiscardedDefenseAction(p, this.block));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseSecondMagicNumber = GameActionManager.totalDiscardedThisTurn;
        initializeDescription();
    }

    public void onMoveToDiscard() {
        resetAttributes();
        this.initializeDescription();
    }

    @Override
    protected void upgradeValues() {
        upgradeBlock(UPGRADE_PLUS_BLOCK);
    }
}

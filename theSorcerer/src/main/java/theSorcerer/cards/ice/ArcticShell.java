package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;

public class ArcticShell extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 3;
    private static final int DRAW_CARD = 1;
    public boolean lastCardPlayedIsFire = false;
    // --- VALUES END ---

    public ArcticShell() {
        super(
                DynamicCard.InfoBuilder(ArcticShell.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.SELF)
                        .magicNumber(DRAW_CARD)
                        .block(BLOCK)
        );
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard card) {
        this.lastCardPlayedIsFire = DynamicDungeon.isIceCard(card);
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new GainBlockAction(
                        player,
                        player,
                        this.block
                )
        );

        if (this.lastCardPlayedIsFire) {
            DynamicDungeon.drawCard(this.magicNumber);
        }
    }

    @Override
    protected void upgradeValues() {
        upgradeBlock(UPGRADE_BLOCK);
    }
}

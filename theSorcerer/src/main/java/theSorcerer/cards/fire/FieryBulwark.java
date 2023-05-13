package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.special.FireCharm;

public class FieryBulwark extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int TOKEN_AMOUNT = 1;
    // --- VALUES END ---

    public FieryBulwark() {
        super(
                DynamicCard.InfoBuilder(FieryBulwark.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.SELF)
                        .block(BLOCK)
                        .magicNumber(TOKEN_AMOUNT)
        );

        this.cardsToPreview = new FireCharm();
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

        AbstractCard card = this.cardsToPreview.makeStatEquivalentCopy();
        if (this.upgraded) {
            card.upgrade();
        }
        addToBot(
                new MakeTempCardInDrawPileAction(
                        card,
                        this.magicNumber,
                        true,
                        true
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeBlock(UPGRADE_PLUS_BLOCK);
        this.cardsToPreview.upgrade();
    }
}

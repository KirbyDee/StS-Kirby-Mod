package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;

public class SparkingTendril extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int PLUS_BLOCK = 3;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    // --- VALUES END ---

    public SparkingTendril() {
        super(
                DynamicCard.InfoBuilder(SparkingTendril.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.SELF)
                        .magicNumber(PLUS_BLOCK)
                        .secondMagicNumber(0)
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(
                new GainBlockAction(
                        p,
                        p,
                        this.secondMagicNumber
                )
        );
    }

    public void applyPowers() {
        super.applyPowers();
        this.baseSecondMagicNumber = AbstractDungeon.player.drawPile.size() + this.baseMagicNumber;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        initializeDescription();
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_PLUS_BLOCK);
    }
}

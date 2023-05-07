package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.actions.SelfFulfillingProphecyAction;

public class SelfFulfillingProphecy extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int CARDS_TO_PUT_TO_DRAW_PILE = 1;
    private static final int CARDS_TO_DRAW = 1;
    // --- VALUES END ---

    public SelfFulfillingProphecy() {
        super(
                DynamicCard.InfoBuilder(SelfFulfillingProphecy.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.COMMON)
                        .magicNumber(CARDS_TO_PUT_TO_DRAW_PILE)
                        .secondMagicNumber(CARDS_TO_DRAW)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new SelfFulfillingProphecyAction(
                        this.magicNumber
                )
        );
        DynamicDungeon.drawCard(this.secondMagicNumber);
    }

    @Override
    protected void upgradeValues() {
        upgradeBaseCost(UPGRADE_COST);
    }
}

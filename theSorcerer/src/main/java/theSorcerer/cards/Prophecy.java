package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.actions.ProphecyAction;

public class Prophecy extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int CARDS_TO_PUT_TO_DRAW_PILE = 1;
    private static final int CARDS_TO_DRAW = 1;
    // --- VALUES END ---

    public Prophecy() {
        super(
                DynamicCard.InfoBuilder(Prophecy.class)
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
                new ProphecyAction(
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

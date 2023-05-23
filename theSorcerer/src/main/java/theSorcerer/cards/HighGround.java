package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;

public class HighGround extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int DRAW_CARDS = 2;
    private static final int UPGRADE_DRAW_CARDS = 1;
    // --- VALUES END ---

    public HighGround() {
        super(
                DynamicCard.InfoBuilder(HighGround.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .magicNumber(DRAW_CARDS)
                        .modifiers(CardModifier.ELEMENTCOST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        DynamicDungeon.drawCard(this.magicNumber);
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_DRAW_CARDS);
    }
}

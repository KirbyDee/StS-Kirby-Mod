package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.modifiers.CardModifier;

public class Fortell extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    // --- VALUES END ---

    public Fortell() {
        super(
                DynamicCard.InfoBuilder(Fortell.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.SELF)
                        .block(BLOCK)
                        .modifiers(CardModifier.FUTURITY)
        );
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
    }

    @Override
    protected void upgradeValues() {
        upgradeBlock(UPGRADE_PLUS_BLOCK);
    }
}

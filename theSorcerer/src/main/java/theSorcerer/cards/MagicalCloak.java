package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.IncreaseMagicalCloakBlockAction;
import theSorcerer.patches.cards.CardAbility;

public class MagicalCloak extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 0;
    private static final int BLOCK = 3;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int BLOCK_INCREASE_PER_USE = 2;
    // --- VALUES END ---

    public MagicalCloak() {
        super(
                DynamicCard.InfoBuilder(MagicalCloak.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.SELF)
                        .magicNumber(BLOCK_INCREASE_PER_USE)
                        .block(BLOCK)
                        .abilities(CardAbility.INNATE)
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // gain block
        addToBot(
                new GainBlockAction(p, p, this.block)
        );

        // increase blocks for all Cloaks in deck
        addToBot(
                new IncreaseMagicalCloakBlockAction(this, this.magicNumber)
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeBlock(UPGRADE_PLUS_BLOCK);
    }
}

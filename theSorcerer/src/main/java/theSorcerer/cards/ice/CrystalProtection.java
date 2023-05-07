package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.CrystalProtectionAction;
import theSorcerer.cards.DynamicCard;

public class CrystalProtection extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    // --- VALUES END ---

    public CrystalProtection() {
        super(
                DynamicCard.InfoBuilder(CrystalProtection.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.SELF)
                        .block(BLOCK)
        );
    }

    // TODOO: sfx?
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // block
        addToBot(
                new GainBlockAction(
                        p,
                        p,
                        this.block
                )
        );

        addToBot(new WaitAction(0.25F));

        // search another crystal protection
        addToBot(
                new CrystalProtectionAction()
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeBlock(UPGRADE_PLUS_BLOCK);
    }
}

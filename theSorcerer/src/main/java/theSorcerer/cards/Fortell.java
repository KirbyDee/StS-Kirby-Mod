package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theSorcerer.modifiers.CardModifier;

public class Fortell extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int DEXTERITY_GAIN = 1;
    private static final int UPGRADE_DEXTERITY_GAIN = 1;
    // --- VALUES END ---

    public Fortell() {
        super(
                DynamicCard.InfoBuilder(Fortell.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.SELF)
                        .block(BLOCK)
                        .magicNumber(DEXTERITY_GAIN)
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
    public void triggerOnFuturity() {
        superFlash();
        addToBot(
                new ApplyPowerAction(
                        AbstractDungeon.player,
                        AbstractDungeon.player,
                        new DexterityPower(AbstractDungeon.player, this.magicNumber),
                        this.magicNumber
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeBlock(UPGRADE_PLUS_BLOCK);
        upgradeMagicNumber(UPGRADE_DEXTERITY_GAIN);
    }
}

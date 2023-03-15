package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.powers.buff.WallOfFirePower;

public class WallOfFire extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 2;
    // --- VALUES END ---

    public WallOfFire() {
        super(
                DynamicCard.InfoBuilder(WallOfFire.class)
                        .cost(COST)
                        .type(CardType.POWER)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.SELF)
                        .magicNumber(BLOCK)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ApplyPowerAction(
                        player,
                        player,
                        new WallOfFirePower(player, this.magicNumber),
                        this.magicNumber
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_BLOCK);
    }

}

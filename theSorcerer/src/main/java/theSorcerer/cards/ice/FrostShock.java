package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.fire.SorcererFireCard;
import theSorcerer.powers.buff.FrostShockPower;

public class FrostShock extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 2;
    // --- VALUES END ---

    public FrostShock() {
        super(
                DynamicCard.InfoBuilder(FrostShock.class)
                        .cost(COST)
                        .type(CardType.POWER)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.SELF)
                        .magicNumber(DAMAGE)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ApplyPowerAction(
                        player,
                        player,
                        new FrostShockPower(player, this.magicNumber),
                        this.magicNumber
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_DAMAGE);
    }

}

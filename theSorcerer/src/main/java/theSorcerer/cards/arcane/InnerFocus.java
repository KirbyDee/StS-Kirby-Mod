package theSorcerer.cards.arcane;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.powers.buff.InnerFocusPower;

public class InnerFocus extends SorcererArcaneCard {

    // --- VALUES START ---
    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;
    private static final int ENERGY_GAIN = 1;
    // --- VALUES END ---

    public InnerFocus() {
        super(
                DynamicCard.InfoBuilder(InnerFocus.class)
                        .cost(COST)
                        .type(CardType.POWER)
                        .rarity(CardRarity.RARE)
                        .magicNumber(ENERGY_GAIN)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ApplyPowerAction(
                        player,
                        player,
                        new InnerFocusPower(player, this.magicNumber),
                        this.magicNumber
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeBaseCost(UPGRADE_COST);
    }

}

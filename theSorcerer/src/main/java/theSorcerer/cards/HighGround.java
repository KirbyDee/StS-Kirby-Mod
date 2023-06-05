package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.CardCostIncreaseAction;

public class HighGround extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int CARD_AMOUNT_DRAW = 1;
    private static final int UPGRADE_CARD_AMOUNT_DRAW = 1;
    private static final int CARD_AMOUNT_TO_INCREASE = 1;
    private static final int CARD_COST_INCREASE = 1;
    private static final int UPGRADE_CARD_COST_INCREASE = 1;
    private static final int ENERGY_GAIN = 1;
    private static final int UPGRADE_ENERGY_GAIN = 1;
    // --- VALUES END ---

    public HighGround() {
        super(
                DynamicCard.InfoBuilder(HighGround.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .magicNumber(CARD_AMOUNT_DRAW)
                        .secondMagicNumber(CARD_COST_INCREASE)
                        .thirdMagicNumber(ENERGY_GAIN)
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // draw cards
        addToBot(new DrawCardAction(this.magicNumber));

        // increase cost this turn of a card in hand
        addToBot(new CardCostIncreaseAction(CARD_AMOUNT_TO_INCREASE, this.secondMagicNumber));

        // increase energy
        addToBot(new GainEnergyAction(this.thirdMagicNumber));
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_CARD_AMOUNT_DRAW);
        upgradeSecondMagicNumber(UPGRADE_CARD_COST_INCREASE);
        upgradeThirdMagicNumber(UPGRADE_ENERGY_GAIN);
    }
}

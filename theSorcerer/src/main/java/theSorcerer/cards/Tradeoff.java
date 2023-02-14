package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.CardCostIncreaseAction;

public class Tradeoff extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int CARD_AMOUNT_DRAW = 1;
    private static final int UPGRADE_CARD_AMOUNT_DRAW = 1;
    private static final int CARD_AMOUNT_TO_INCREASE = 1;
    private static final int CARD_COST_INCREASE = 1;
    private static final int UPGRADE_CARD_COST_INCREASE = 1;
    private static final int ENERGY_GAIN = 1;
    private static final int UPGRADE_ENERGY_GAIN = 2;
    // --- VALUES END ---

    public Tradeoff() {
        super(
                DynamicCard.InfoBuilder(Tradeoff.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .abilities(CardAbility.FLASHBACK)
        );
        this.baseMagicNumber = CARD_AMOUNT_DRAW;
        this.baseSecondMagicNumber = CARD_COST_INCREASE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // draw cards
        addToBot(new DrawCardAction(this.upgraded ? UPGRADE_CARD_AMOUNT_DRAW : CARD_AMOUNT_DRAW));

        // increase cost this turn of a card in hand
        addToBot(new CardCostIncreaseAction(CARD_AMOUNT_TO_INCREASE, this.upgraded ? UPGRADE_CARD_COST_INCREASE : CARD_COST_INCREASE));

        // increase energy
        addToBot(new GainEnergyAction(this.upgraded ? UPGRADE_ENERGY_GAIN : ENERGY_GAIN)); // TODO in strings
    }

    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_CARD_AMOUNT_DRAW);
        upgradeSecondMagicNumber(UPGRADE_CARD_COST_INCREASE);
    }
}

package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.powers.buff.UneducatedGuessPower;

public class UneducatedGuess extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    private static final int DRAW_CARD_NEXT_TURN = 1;
    // --- VALUES END ---

    public UneducatedGuess() {
        super(
                DynamicCard.InfoBuilder(UneducatedGuess.class)
                        .cost(COST)
                        .type(CardType.POWER)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.SELF)
                        .tags(SorcererCardTags.ELEMENTLESS)
                        .magicNumber(DRAW_CARD_NEXT_TURN)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ApplyPowerAction(
                        player,
                        player,
                        new UneducatedGuessPower(player, this.magicNumber),
                        this.magicNumber
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeBaseCost(UPGRADE_COST);
    }

}

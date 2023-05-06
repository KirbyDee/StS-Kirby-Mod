package theSorcerer.cards.special;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;
import theSorcerer.patches.cards.CardAbility;

public class HeatingCharm extends SorcererSpecialCard {

    // --- VALUES START ---
    private static final int COST = 0;
    private static final int TOKEN_AMOUNT = 1;
    private static final int UPGRADE_TOKEN_AMOUNT = 1;
    // --- VALUES END ---

    public HeatingCharm() {
        super(
                DynamicCard.InfoBuilder(HeatingCharm.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .magicNumber(TOKEN_AMOUNT)
                        .abilities(CardAbility.ETHEREAL, CardAbility.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        DynamicDungeon.applyHeated(this.magicNumber);
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_TOKEN_AMOUNT);
    }
}

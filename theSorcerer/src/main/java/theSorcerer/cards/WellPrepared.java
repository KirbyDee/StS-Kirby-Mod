package theSorcerer.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.modifiers.FlashbackMod;
import theSorcerer.patches.cards.CardAbility;

public class WellPrepared extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 0;
    private static final int CARD_AMOUNT_DRAW = 2;
    private static final int UPGRADE_CARD_AMOUNT_DRAW = 1;
    // --- VALUES END ---

    public WellPrepared() {
        super(
                DynamicCard.InfoBuilder(WellPrepared.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.RARE)
                        .abilities(
                                CardAbility.FUTURITY,
                                CardAbility.FLASHBACK
                        )
                        .magicNumber(CARD_AMOUNT_DRAW)
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber));
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_CARD_AMOUNT_DRAW);
    }
}

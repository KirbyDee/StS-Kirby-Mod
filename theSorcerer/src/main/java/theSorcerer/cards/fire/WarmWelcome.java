package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;
import theSorcerer.modifiers.CardModifier;

public class WarmWelcome extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    // --- VALUES END ---

    public WarmWelcome() {
        super(
                DynamicCard.InfoBuilder(WarmWelcome.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .modifiers(CardModifier.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractCard c = DynamicDungeon.returnRandomFireCard().makeCopy();
        addToBot(
                new MakeTempCardInHandAction(
                        c,
                        true
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeBaseCost(UPGRADE_COST);
    }
}

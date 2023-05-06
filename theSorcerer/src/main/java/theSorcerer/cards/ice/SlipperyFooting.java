package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;
import theSorcerer.patches.cards.CardAbility;

public class SlipperyFooting extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    // --- VALUES END ---

    public SlipperyFooting() {
        super(
                DynamicCard.InfoBuilder(SlipperyFooting.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .abilities(CardAbility.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractCard c = DynamicDungeon.returnRandomIceCardInCombat().makeCopy();
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

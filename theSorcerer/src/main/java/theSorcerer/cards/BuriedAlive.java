package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.PutCardFromDrawPileToDiscardPileAction;
import theSorcerer.modifiers.CardModifier;

public class BuriedAlive extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int CARDS_TO_CHOOSE = 1;
    // --- VALUES END ---

    public BuriedAlive() {
        super(
                DynamicCard.InfoBuilder(BuriedAlive.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.NONE)
                        .magicNumber(CARDS_TO_CHOOSE)
                        .modifiers(CardModifier.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new PutCardFromDrawPileToDiscardPileAction(this.magicNumber)
        );
    }

    @Override
    public boolean canUse(AbstractPlayer player, AbstractMonster monster) {
        boolean canUse = super.canUse(player, monster);
        if (!canUse) {
            return false;
        }
        else if (AbstractDungeon.player.drawPile.group.size() < this.magicNumber) {
            this.cantUseMessage = baseExtendedDescriptions[0];
            return false;
        }
        return true;
    }

    @Override
    protected void upgradeValues() {
        upgradeBaseCost(UPGRADE_COST);
    }
}

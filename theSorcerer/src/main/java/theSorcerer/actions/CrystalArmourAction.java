package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.cards.ice.CrystalArmour;

public class CrystalArmourAction extends DrawPileCardChooseAction {

    private static final int AMOUNT = 1;

    public CrystalArmourAction() {
        super(AMOUNT, true);
        this.actionType = ActionType.DRAW;
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return card instanceof CrystalArmour;
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        AbstractDungeon.player.drawPile.moveToHand(card);
    }

    @Override
    protected String getChooseText() {
        return "Choose another Crystal Protection to put into your hand"; // TODOO
    }
}

package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.cards.fire.Fireball;
import theSorcerer.cards.ice.CrystalProtection;

public class CrystalProtectionAction extends DrawPileCardChooseAction {

    private static final int AMOUNT = 1;

    public CrystalProtectionAction() {
        super(AMOUNT, true);
        this.actionType = ActionType.DRAW;
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return card instanceof CrystalProtection;
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

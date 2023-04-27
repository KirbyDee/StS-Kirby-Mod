package theSorcerer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.cards.fire.Fireball;

public class FireballAction extends DrawPileCardChooseAction {

    private static final int AMOUNT = 1;

    public FireballAction() {
        super(AMOUNT, true);
    }

    @Override
    protected boolean canBeChosen(final AbstractCard card) {
        return card instanceof Fireball;
    }

    @Override
    protected void onCardChosen(AbstractCard card) {
        AbstractDungeon.player.drawPile.moveToHand(card);
    }

    @Override
    protected String getChooseText() {
        return "Choose another Fireball to put into your hand"; // TODOO
    }
}

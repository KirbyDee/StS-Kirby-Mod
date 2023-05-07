package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AutoUseCardAction extends AbstractGameAction {

    private final AbstractPlayer player;

    private final AbstractCard card;

    public AutoUseCardAction(
            AbstractPlayer player,
            AbstractCard card
    ) {
        this.player = player;
        this.card = card;
        this.actionType = ActionType.USE;
    }

    @Override
    public void update() {
        if (this.card.hasEnoughEnergy()) {
            this.player.useCard(this.card, AbstractDungeon.getRandomMonster(), this.card.energyOnUse);
        }
        else {
            this.player.hand.moveToExhaustPile(this.card);
        }
        this.isDone = true;
    }
}

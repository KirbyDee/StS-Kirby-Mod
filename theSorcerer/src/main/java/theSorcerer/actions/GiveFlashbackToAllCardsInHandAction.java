package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;

public class GiveFlashbackToAllCardsInHandAction extends AbstractGameAction {

    private final AbstractPlayer player;

    public GiveFlashbackToAllCardsInHandAction() {
        this.player = AbstractDungeon.player;
    }

    @Override
    public void update() {
        this.player.hand.group
                .stream()
                .filter(c -> !DynamicDungeon.isFlashbackCard(c))
                .forEach(this::applyFlashback);

        this.isDone = true;
    }

    private void applyFlashback(final AbstractCard card) {
        DynamicDungeon.makeCardFlashback(card);

        // apply flash, powers and init shown description
        card.superFlash();
        card.applyPowers();
        DynamicDungeon.updateAbilityDescription(card);
    }
}

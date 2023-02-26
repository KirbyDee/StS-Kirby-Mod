package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.cards.MagicalCloak;
import theSorcerer.cards.DynamicCard;

public class IncreaseMagicalCloakBlockAction extends AbstractGameAction {

    private final DynamicCard card;

    public IncreaseMagicalCloakBlockAction(
            DynamicCard card,
            int amount
    ) {
        this.card = card;
        this.amount = amount;
    }

    public void update() {
        // increase this cards block
        this.card.baseBlock += this.amount;
        this.card.applyPowers();

        // for each card group, increase block
        increaseCloakBlockOfCardGroup(AbstractDungeon.player.discardPile);
        increaseCloakBlockOfCardGroup(AbstractDungeon.player.drawPile);
        increaseCloakBlockOfCardGroup(AbstractDungeon.player.hand);

        this.isDone = true;
    }

    private void increaseCloakBlockOfCardGroup(final CardGroup cardGroup) {
        cardGroup.group
                .stream()
                .filter(MagicalCloak.class::isInstance)
                .forEach(c -> {
                    c.baseBlock += this.amount;
                    c.applyPowers();
                });
    }
}

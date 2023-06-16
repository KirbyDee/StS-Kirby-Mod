package theSorcerer.actions;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;

public class CopycatCardsAction extends AbstractGameAction {

    private final AbstractCard card;

    public CopycatCardsAction(AbstractCard card) {
        setValues(AbstractDungeon.player, this.source, -1);
        this.card = card;
    }

    public void update() {
        updateCard();
        this.isDone = true;
    }

    private AbstractCard copycatToHand(final AbstractCard card) {
        final AbstractCard copycatCard = createCopycat(card);
        copycatCard.superFlash();
        copycatCard.onRetained();
        CardCrawlGame.sound.play("POWER_MANTRA", 0.1f);
        return copycatCard;
    }

    private void updateCard() {
        if (!DynamicDungeon.isCopycatCard(this.card)) {
            return;
        }

        final AbstractCard copyCard = copycatToHand(this.card);
        AbstractDungeon.player.hand.group.set(AbstractDungeon.player.hand.group.indexOf(this.card), copyCard);
    }

    private AbstractCard createCopycat(final AbstractCard card) {
        // get random card in deck (but no strike, defend if upgraded)
        CustomCard copyCard = DynamicDungeon.getCopyOfRandomCardInDeck(
                card.upgraded ?
                        c -> !c.tags.contains(AbstractCard.CardTags.STARTER_DEFEND) && !c.tags.contains(AbstractCard.CardTags.STARTER_STRIKE) :
                        c -> true
        );
        if (copyCard == null) {
            return card;
        }

        // make it copycat
        if (DynamicDungeon.cardHasModifier(card, CardModifier.COPYCAT_PLUS)) {
            DynamicDungeon.addModifierToCard(copyCard, CardModifier.COPYCAT_PLUS);
            copyCard.upgrade();
        }
        else {
            DynamicDungeon.addModifierToCard(copyCard, CardModifier.COPYCAT);
        }
        copyCard.costForTurn = 0;
        return copyCard;
    }
}

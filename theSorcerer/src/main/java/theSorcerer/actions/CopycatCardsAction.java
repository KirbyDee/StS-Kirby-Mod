package theSorcerer.actions;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;

import java.util.Iterator;

public class CopycatCardsAction extends AbstractGameAction {

    private final CardGroup group;

    public CopycatCardsAction(CardGroup group) {
        setValues(AbstractDungeon.player, this.source, -1);
        this.group = group;
    }

    public void update() {
        this.isDone = true;
        Iterator<AbstractCard> c = this.group.group.iterator();

        while(true) {
            AbstractCard e;
            do {
                if (!c.hasNext()) {
                    AbstractDungeon.player.hand.refreshHandLayout();
                    return;
                }

                e = c.next();
            } while (!DynamicDungeon.isCopycatCard(e));

            final AbstractCard copycatCard = createCopycat(e);
            AbstractDungeon.player.hand.addToTop(copycatCard);
            copycatCard.superFlash();
            c.remove();
        }
    }

    private AbstractCard createCopycat(final AbstractCard card) {
        // get random card in deck (but no strike, defend)
        CustomCard copyCard = DynamicDungeon.getCopyOfRandomCardInDeck();
        if (copyCard == null) {
            return card;
        }

        // make it retain / copycat
        DynamicDungeon.addModifierToCard(copyCard, CardModifier.COPYCAT);

        // if upgraded
        copyCard.upgrade();
        if (card.upgraded) {
            copyCard.costForTurn = 0;
        }
        return copyCard;
    }
}

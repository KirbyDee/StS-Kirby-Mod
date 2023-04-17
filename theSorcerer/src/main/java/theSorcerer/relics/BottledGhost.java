package theSorcerer.relics;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSorcerer.patches.cards.AbstractCardPatch;

import java.util.function.Predicate;

public class BottledGhost extends BottledRelic implements CustomSavable<Integer> {

    public BottledGhost() {
        super(
                BottledGhost.class,
                RelicTier.UNCOMMON
        );
    }

    @Override
    protected CardGroup getCardGroup() {
        return AbstractDungeon.player.masterDeck;
    }

    @Override
    protected void onRemoveBottledCard(AbstractCard card) {
        AbstractCardPatch.inBottleGhost.set(card, false);
    }

    @Override
    protected void onAddBottledCard(AbstractCard card) {
        AbstractCardPatch.inBottleGhost.set(this.card, true);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BottledGhost();
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return AbstractCardPatch.inBottleGhost::get;
    }

    @Override
    public Integer onSave() {
        if (this.card != null) {
            return AbstractDungeon.player.masterDeck.group.indexOf(this.card);
        } else {
            return -1;
        }
    }

    @Override
    public void onLoad(Integer cardIndex) {
        if (cardIndex == null) {
            return;
        }
        if (cardIndex >= 0 && cardIndex < AbstractDungeon.player.masterDeck.group.size()) {
            this.card = AbstractDungeon.player.masterDeck.group.get(cardIndex);
            if (this.card != null) {
                onAddBottledCard(this.card);
                setDescriptionAfterLoading();
            }
        }
    }
}

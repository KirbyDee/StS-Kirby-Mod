package theSorcerer.relics;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSorcerer.DynamicDungeon;
import theSorcerer.patches.cards.AbstractCardPatch;

import java.util.function.Predicate;

public class BottledEnergy extends BottledRelic implements CustomSavable<Integer> {

    public BottledEnergy() {
        super(
                BottledEnergy.class,
                RelicTier.RARE
        );
    }

    @Override
    protected CardGroup getCardGroup() {
        CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        AbstractDungeon.player.masterDeck.getPurgeableCards().group
                .stream()
                .filter(c -> !DynamicDungeon.isArcaneCard(c))
                .forEach(c -> cardGroup.group.add(c));
        return cardGroup;
    }

    @Override
    protected void onRemoveBottledCard(AbstractCard card) {
        AbstractCardPatch.inBottleEnergy.set(card, false);
    }

    @Override
    protected void onAddBottledCard(AbstractCard card) {
        AbstractCardPatch.inBottleEnergy.set(this.card, true);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BottledEnergy();
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return AbstractCardPatch.inBottleEnergy::get;
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

package theSorcerer.relics;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSorcerer.DynamicDungeon;
import theSorcerer.KirbyDeeMod;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.util.TextureLoader;

import java.util.function.Predicate;

import static theSorcerer.KirbyDeeMod.makeRelicOutlinePath;
import static theSorcerer.KirbyDeeMod.makeRelicPath;

public class BottledEnergy extends BottledRelic implements CustomSavable<Integer> {

    public static final String ID = KirbyDeeMod.makeID("BottledEnergy");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public BottledEnergy() {
        super(ID, IMG, OUTLINE, RelicTier.RARE);
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

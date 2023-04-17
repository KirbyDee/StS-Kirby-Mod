package theSorcerer.relics;

import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSorcerer.KirbyDeeMod;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.util.TextureLoader;

import java.util.function.Predicate;

import static theSorcerer.KirbyDeeMod.makeRelicOutlinePath;
import static theSorcerer.KirbyDeeMod.makeRelicPath;

public class BottledTombstone extends BottledRelic implements CustomSavable<Integer> {

    public static final String ID = KirbyDeeMod.makeID("BottledTombstone");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public BottledTombstone() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON);
    }

    @Override
    protected CardGroup getCardGroup() {
        return AbstractDungeon.player.masterDeck;
    }

    @Override
    protected void onRemoveBottledCard(AbstractCard card) {
        AbstractCardPatch.inBottleTombstone.set(card, false);
    }

    @Override
    protected void onAddBottledCard(AbstractCard card) {
        AbstractCardPatch.inBottleTombstone.set(this.card, true);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new BottledTombstone();
    }

    @Override
    public Predicate<AbstractCard> isOnCard() {
        return AbstractCardPatch.inBottleTombstone::get;
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

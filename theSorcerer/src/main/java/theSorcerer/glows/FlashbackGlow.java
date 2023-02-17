package theSorcerer.glows;

import basemod.helpers.CardBorderGlowManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.cards.SorcererCardTags;

// TODO doesn't work?
public class FlashbackGlow extends CardBorderGlowManager.GlowInfo {

    public static final String GLOW_ID = "theSorcerer:FlashbackGlow";

    @Override
    public boolean test(AbstractCard card) {
        return card.hasTag(SorcererCardTags.FLASHBACK) && AbstractDungeon.player.discardPile.group.contains(card);
    }

    @Override
    public Color getColor(AbstractCard card) {
        return Color.GREEN.cpy();
    }

    @Override
    public String glowID() {
        return GLOW_ID;
    }
}

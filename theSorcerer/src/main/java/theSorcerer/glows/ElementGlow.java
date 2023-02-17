package theSorcerer.glows;

import basemod.helpers.CardBorderGlowManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.cards.SorcererCardTags;

public class ElementGlow extends CardBorderGlowManager.GlowInfo {

    public static final String GLOW_ID = "theSorcerer:ElementGlow";

    @Override
    public boolean test(AbstractCard card) {
        return card.hasTag(SorcererCardTags.FIRE) || card.hasTag(SorcererCardTags.ICE);
    }

    @Override
    public Color getColor(AbstractCard card) {
        if (card.hasTag(SorcererCardTags.FIRE)) {
            return Color.SCARLET.cpy();
        }
        else if (card.hasTag(SorcererCardTags.ICE)) {
            return Color.ROYAL.cpy();
        }
        return Color.WHITE.cpy();
    }

    @Override
    public String glowID() {
        return GLOW_ID;
    }
}

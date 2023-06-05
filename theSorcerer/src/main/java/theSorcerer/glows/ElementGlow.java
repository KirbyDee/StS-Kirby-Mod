package theSorcerer.glows;

import basemod.helpers.CardBorderGlowManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.cards.fire.Combustion;
import theSorcerer.cards.ice.ArcticShell;
import theSorcerer.powers.debuff.ElementlessPower;

public class ElementGlow extends CardBorderGlowManager.GlowInfo {

    public static final String GLOW_ID = "theSorcerer:ElementGlow";

    @Override
    public boolean test(AbstractCard card) {
        return !DynamicDungeon.hasElementless() && DynamicDungeon.isElementCard(card);
    }

    @Override
    public Color getColor(AbstractCard card) {
        if (DynamicDungeon.isFireCard(card)) {
            if (card instanceof Combustion && ((Combustion) card).glowCheckTriggered) {
                return Color.GOLD.cpy();
            }
            return Color.SCARLET.cpy();
        }
        else if (DynamicDungeon.isIceCard(card)) {
            if (card instanceof ArcticShell && ((ArcticShell) card).glowCheckTriggered) {
                return Color.GOLD.cpy();
            }
            return Color.NAVY.cpy();
        }
        return Color.WHITE.cpy();
    }

    @Override
    public String glowID() {
        return GLOW_ID;
    }
}

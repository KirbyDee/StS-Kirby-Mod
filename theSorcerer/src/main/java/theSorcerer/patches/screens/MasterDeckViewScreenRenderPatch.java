package theSorcerer.patches.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.MasterDeckViewScreen;
import javassist.CtBehavior;
import theSorcerer.patches.cards.AbstractCardPatch;

@SpirePatch(clz = MasterDeckViewScreen.class, method = "render")
public class MasterDeckViewScreenRenderPatch {

    // TODO: doesn't work?
    @SpireInsertPatch(locator = Locator.class)
    public static void renderPatch(
            MasterDeckViewScreen self,
            SpriteBatch sb,
            @ByRef(type="cards.AbstractCard") Object[] ___hoveredCard
    ) {
        if (AbstractCardPatch.inBottleEnergy.get((___hoveredCard[0]))) {
            renderBottledCard(sb, ___hoveredCard);
        }
        else if (AbstractCardPatch.inBottleEnergy.get((___hoveredCard[0]))) {
            renderBottledCard(sb, ___hoveredCard);
        }
    }

    private static void renderBottledCard(
            final SpriteBatch sb,
            Object[] ___hoveredCard
    ) {
        AbstractRelic tmp = RelicLibrary.getRelic("Bottled Flame");
        float prevX = tmp.currentX;
        float prevY = tmp.currentY;
        tmp.currentX = ((AbstractCard)___hoveredCard[0]).current_x + 130.0F * Settings.scale;
        tmp.currentY = ((AbstractCard)___hoveredCard[0]).current_y + 182.0F * Settings.scale;
        tmp.scale = ((AbstractCard)___hoveredCard[0]).drawScale * Settings.scale * 1.5F;
        tmp.render(sb);
        tmp.currentX = prevX;
        tmp.currentY = prevY;
        tmp = null;
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "inBottleFlame");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
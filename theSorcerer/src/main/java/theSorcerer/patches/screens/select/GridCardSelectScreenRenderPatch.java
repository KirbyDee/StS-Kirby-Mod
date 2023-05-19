package theSorcerer.patches.screens.select;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import javassist.CtBehavior;

import java.util.function.Consumer;

@SpirePatch(clz = GridCardSelectScreen.class, method = "render")
public class GridCardSelectScreenRenderPatch {

    @SpireInsertPatch(locator = Locator.class)
    public static SpireReturn<Void> renderPatch(
            GridCardSelectScreen self,
            SpriteBatch sb,
            @ByRef(type="cards.AbstractCard") Object[] ___hoveredCard,
            @ByRef float[] ___arrowTimer,
            @ByRef float[] ___arrowScale1,
            @ByRef float[] ___arrowScale2,
            @ByRef float[] ___arrowScale3,
            @ByRef String[] ___tipMsg
    ) {
        Consumer<AbstractCard> applyModificationToCard = GridCardSelectScreenPatch.forModifyCardConsumer.get(self);
        if (self.upgradePreviewCard == null || applyModificationToCard == null) {
            return SpireReturn.Continue();
        }

        renderArrows(sb, ___arrowTimer, ___arrowScale1, ___arrowScale2, ___arrowScale3);
        applyModificationToCard.accept(self.upgradePreviewCard);
        ((AbstractCard)___hoveredCard[0]).current_x = (float)Settings.WIDTH * 0.36F;
        ((AbstractCard)___hoveredCard[0]).current_y = (float)Settings.HEIGHT / 2.0F;
        ((AbstractCard)___hoveredCard[0]).target_x = (float)Settings.WIDTH * 0.36F;
        ((AbstractCard)___hoveredCard[0]).target_y = (float)Settings.HEIGHT / 2.0F;
        ((AbstractCard)___hoveredCard[0]).render(sb);
        ((AbstractCard)___hoveredCard[0]).updateHoverLogic();
        ((AbstractCard)___hoveredCard[0]).renderCardTip(sb);
        self.upgradePreviewCard.current_x = (float)Settings.WIDTH * 0.63F;
        self.upgradePreviewCard.current_y = (float)Settings.HEIGHT / 2.0F;
        self.upgradePreviewCard.target_x = (float)Settings.WIDTH * 0.63F;
        self.upgradePreviewCard.target_y = (float)Settings.HEIGHT / 2.0F;
        self.upgradePreviewCard.render(sb);
        self.upgradePreviewCard.updateHoverLogic();
        self.upgradePreviewCard.renderCardTip(sb);

        if (!PeekButton.isPeeking) {
            self.confirmButton.render(sb);
        }

        self.peekButton.render(sb);
        if ((!self.isJustForConfirming || self.targetGroup.size() > 5) && !PeekButton.isPeeking) {
            FontHelper.renderDeckViewTip(sb, ___tipMsg[0], 96.0F * Settings.scale, Settings.CREAM_COLOR);
        }

        return SpireReturn.Return();
    }

    private static void renderArrows(
            SpriteBatch sb,
            float[] arrowTimer,
            float[] arrowScale1,
            float[] arrowScale2,
            float[] arrowScale3
    ) {
        float x = (float)Settings.WIDTH / 2.0F - 73.0F * Settings.scale - 32.0F;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, arrowScale1[0] * Settings.scale, arrowScale1[0] * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        x += 64.0F * Settings.scale;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, arrowScale2[0] * Settings.scale, arrowScale2[0] * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        x += 64.0F * Settings.scale;
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, arrowScale3[0] * Settings.scale, arrowScale3[0] * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        arrowTimer[0] += Gdx.graphics.getDeltaTime() * 2.0F;
        arrowScale1[0] = 0.8F + (MathUtils.cos(arrowTimer[0]) + 1.0F) / 8.0F;
        arrowScale2[0] = 0.8F + (MathUtils.cos(arrowTimer[0] - 0.8F) + 1.0F) / 8.0F;
        arrowScale3[0] = 0.8F + (MathUtils.cos(arrowTimer[0] - 1.6F) + 1.0F) / 8.0F;
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(GridCardSelectScreen.class, "forUpgrade");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
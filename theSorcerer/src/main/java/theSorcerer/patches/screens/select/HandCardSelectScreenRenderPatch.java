package theSorcerer.patches.screens.select;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import javassist.CtBehavior;
import theSorcerer.DynamicDungeon;

import java.util.function.Consumer;

@SpirePatch(clz = HandCardSelectScreen.class, method = "render")
public class HandCardSelectScreenRenderPatch {

    @SpireInsertPatch(locator = Locator.class, localvars = {"arrowTimer", "arrowScale1", "arrowScale2", "arrowScale3"})
    public static void renderPatch(
            HandCardSelectScreen self,
            SpriteBatch sb,
            @ByRef float[] ___arrowTimer,
            @ByRef float[] ___arrowScale1,
            @ByRef float[] ___arrowScale2,
            @ByRef float[] ___arrowScale3
    ) {
        Consumer<AbstractCard> applyElementToCard = HandCardSelectScreenPatch.forElementMorphoseField.get(self);
        if (self.upgradePreviewCard == null || applyElementToCard == null) {
            return;
        }

        renderArrows(sb, ___arrowTimer, ___arrowScale1, ___arrowScale2, ___arrowScale3);
        self.upgradePreviewCard.current_x = (float) Settings.WIDTH * 0.63F;
        self.upgradePreviewCard.current_y = (float)Settings.HEIGHT / 2.0F + 160.0F * Settings.scale;
        self.upgradePreviewCard.target_x = (float)Settings.WIDTH * 0.63F;
        self.upgradePreviewCard.target_y = (float)Settings.HEIGHT / 2.0F + 160.0F * Settings.scale;
        applyElementToCard.accept(self.upgradePreviewCard);
        DynamicDungeon.updateAbilityDescription(self.upgradePreviewCard);
        self.upgradePreviewCard.applyPowers();
        self.upgradePreviewCard.render(sb);
        self.upgradePreviewCard.updateHoverLogic();
        self.upgradePreviewCard.renderCardTip(sb);
    }

    private static void renderArrows(
            SpriteBatch sb,
            float[] arrowTimer,
            float[] arrowScale1,
            float[] arrowScale2,
            float[] arrowScale3
    ) {
        float x = (float)Settings.WIDTH / 2.0F - 96.0F * Settings.scale - 10.0F * Settings.scale;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F + 120.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, arrowScale1[0] * Settings.scale, arrowScale1[0] * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        x += 64.0F * Settings.scale;
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F + 120.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, arrowScale2[0] * Settings.scale, arrowScale2[0] * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        x += 64.0F * Settings.scale;
        sb.draw(ImageMaster.UPGRADE_ARROW, x, (float)Settings.HEIGHT / 2.0F + 120.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, arrowScale3[0] * Settings.scale, arrowScale3[0] * Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        arrowTimer[0] += Gdx.graphics.getDeltaTime() * 2.0F;
        arrowScale1[0] = 0.8F + (MathUtils.cos(arrowTimer[0]) + 1.0F) / 8.0F;
        arrowScale2[0] = 0.8F + (MathUtils.cos(arrowTimer[0] - 0.8F) + 1.0F) / 8.0F;
        arrowScale3[0] = 0.8F + (MathUtils.cos(arrowTimer[0] - 1.6F) + 1.0F) / 8.0F;
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(HandCardSelectScreen.class, "forUpgrade");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
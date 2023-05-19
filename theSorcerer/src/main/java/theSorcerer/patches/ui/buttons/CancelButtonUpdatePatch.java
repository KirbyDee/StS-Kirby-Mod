package theSorcerer.patches.ui.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import javassist.CtBehavior;
import theSorcerer.patches.screens.select.GridCardSelectScreenPatch;

import java.util.function.Consumer;

@SpirePatch(clz = CancelButton.class, method = "update")
public class CancelButtonUpdatePatch {

    @SpireInsertPatch(locator = Locator.class)
    public static SpireReturn<Void> updatePatch(
            CancelButton self,
            float ___target_x
    ) {
        Consumer<AbstractCard> applyElementToCard = GridCardSelectScreenPatch.forModifyCardConsumer.get(AbstractDungeon.gridSelectScreen);
        if (applyElementToCard != null && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            if (!AbstractDungeon.gridSelectScreen.confirmScreenUp) {
                AbstractDungeon.closeCurrentScreen();
                if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                    RestRoom r = (RestRoom)AbstractDungeon.getCurrRoom();
                    r.campfireUI.reopen();
                }

                return SpireReturn.Return();
            }

            AbstractDungeon.gridSelectScreen.cancelUpgrade();

            if (self.current_x != ___target_x) {
                self.current_x = MathUtils.lerp(self.current_x, ___target_x, Gdx.graphics.getDeltaTime() * 9.0F);
                if (Math.abs(self.current_x - ___target_x) < Settings.UI_SNAP_THRESHOLD) {
                    self.current_x = ___target_x;
                }
            }
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(GridCardSelectScreen.class, "forUpgrade");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
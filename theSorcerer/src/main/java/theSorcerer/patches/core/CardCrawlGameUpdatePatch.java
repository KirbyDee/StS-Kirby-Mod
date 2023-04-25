package theSorcerer.patches.core;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import javassist.CtBehavior;
import theSorcerer.core.CustomCardCrawlGame;

@SpirePatch(clz = CardCrawlGame.class, method = "update")
public class CardCrawlGameUpdatePatch {

    @SpireInsertPatch(locator = Locator.class)
    public static void updatePatch(CardCrawlGame self) {
        CustomCardCrawlGame.sound.update();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(SoundMaster.class, "update");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
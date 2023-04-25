package theSorcerer.patches.core;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import javassist.CtBehavior;
import theSorcerer.audio.CustomSoundMaster;
import theSorcerer.core.CustomCardCrawlGame;

@SpirePatch(clz = CardCrawlGame.class, method = "create")
public class CardCrawlGameCreatePatch {

    @SpireInsertPatch(locator = Locator.class)
    public static void createPatch(CardCrawlGame self) {
        CustomCardCrawlGame.sound = new CustomSoundMaster();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.NewExprMatcher(SoundMaster.class);
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
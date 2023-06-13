package theSorcerer.patches.actions.common;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import theSorcerer.actions.CopycatCardsAction;

@SpirePatch(clz = DiscardAtEndOfTurnAction.class, method = "update")
public class DiscardAtEndOfTurnActionUpdatePatch {

    @SpireInsertPatch(locator = Locator.class)
    public static void createPatch(DiscardAtEndOfTurnAction self) {
        AbstractDungeon.actionManager.addToTop(new CopycatCardsAction(AbstractDungeon.player.limbo));
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.NewExprMatcher(RestoreRetainedCardsAction.class);
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
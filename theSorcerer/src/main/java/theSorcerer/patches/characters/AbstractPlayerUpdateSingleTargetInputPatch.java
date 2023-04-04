package theSorcerer.patches.characters;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import javassist.CtBehavior;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.debuff.ElementlessPower;

@SpirePatch(clz = AbstractPlayer.class, method = "updateSingleTargetInput")
public class AbstractPlayerUpdateSingleTargetInputPatch {

    @SpireInsertPatch(locator = Locator.class)
    public static void updateSingleTargetInputPatch(AbstractPlayer self) {
        if (DynamicDungeon.isElementCard(self.hoveredCard) && DynamicDungeon.hasElementless()) {
            DynamicDungeon.flashElementlessRelic();
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "cantUseMessage");
            return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
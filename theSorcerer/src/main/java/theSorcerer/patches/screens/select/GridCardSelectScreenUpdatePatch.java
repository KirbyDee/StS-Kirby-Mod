package theSorcerer.patches.screens.select;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import javassist.CtBehavior;

import java.util.function.Consumer;

@SpirePatch(clz = GridCardSelectScreen.class, method = "update")
public class GridCardSelectScreenUpdatePatch {

    @SpireInsertPatch(locator = Locator.class)
    public static SpireReturn<Void> updatePatch(
            GridCardSelectScreen self,
            @ByRef(type="cards.AbstractCard") Object[] ___hoveredCard,
            @ByRef String[] ___tipMsg,
            @ByRef String[] ___lastTip
    ) {
        Consumer<AbstractCard> applyElementToCard = GridCardSelectScreenPatch.forElementMorphoseField.get(self);
        if (applyElementToCard != null) {
            ((AbstractCard)___hoveredCard[0]).untip();
            self.confirmScreenUp = true;
            self.upgradePreviewCard = ((AbstractCard)___hoveredCard[0]).makeStatEquivalentCopy();
            applyElementToCard.accept(self.upgradePreviewCard);
            self.upgradePreviewCard.drawScale = 0.875F;
            ((AbstractCard)___hoveredCard[0]).stopGlowing();
            self.selectedCards.clear();
            AbstractDungeon.overlayMenu.cancelButton.show("Cancel"); // TODOO: "Cancel" - button doesn't work?
            self.confirmButton.show();
            self.confirmButton.isDisabled = false;
            ___lastTip[0] = ___tipMsg[0];
            ___tipMsg[0] = "Confirm your Selection"; // TODOO
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(GridCardSelectScreen.class, "forUpgrade");
            return new int[] {LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[0]};
        }
    }

    @SpireInsertPatch(locator = Locator2.class)
    public static void updatePatch2(
            GridCardSelectScreen self
    ) {
        Consumer<AbstractCard> applyElementToCard = GridCardSelectScreenPatch.forElementMorphoseField.get(self);
        if (applyElementToCard != null) {
            self.upgradePreviewCard.update();
        }
    }

    private static class Locator2 extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(GridCardSelectScreen.class, "forUpgrade");
            return new int[] {LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1]};
        }
    }
}
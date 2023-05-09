package theSorcerer.patches.characters;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.DynamicDungeon;

@SpirePatch(clz = AbstractPlayer.class, method = "preBattlePrep")
public class AbstractPlayerPreBattlePrepPatch {

    private static final Logger LOG = LogManager.getLogger(AbstractPlayerPreBattlePrepPatch.class.getName());

    @SpireInsertPatch(locator = Locator.class)
    public static void preBattlePrepPatch(AbstractPlayer self) {
        LOG.info("Checking if there are any entomb cards in the deck to put them into discard pile");
        CardGroup copy = new CardGroup(self.masterDeck, CardGroup.CardGroupType.DRAW_PILE);
        copy.group
                .stream()
                .filter(DynamicDungeon::isEntombOrBottledGhostCard)
                .forEach(c -> AbstractDungeon.player.discardPile.addToTop(c));

        // resetting elemental cards played
        AbstractPlayerPatch.elementalCardsPlayedPerCombat.set(self, 0);
        AbstractPlayerPatch.arcaneCardsPlayedPerCombat.set(self, 0);
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPlayer.class, "applyPreCombatLogic");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
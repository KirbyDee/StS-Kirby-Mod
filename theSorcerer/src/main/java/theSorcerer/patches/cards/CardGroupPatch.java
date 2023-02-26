package theSorcerer.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

@SpirePatch(clz = CardGroup.class, method = "initializeDeck")
public class CardGroupPatch {

    private static final Logger LOG = LogManager.getLogger(CardGroupPatch.class.getName());

    @SpireInsertPatch(locator = Locator.class, localvars = {"copy"})
    public static void initializeDeckPatch(
            CardGroup self,
            CardGroup masterDeck,
            @ByRef(type="cards.CardGroup") Object[] copy
    ) {
        LOG.info("Checking if there are any entomb cards in the deck to put them NOT in the starting hand");
        CardGroup drawPileCopy = (CardGroup) copy[0];
        List<AbstractCard> entombCards = drawPileCopy.group
                .stream()
                .filter(c -> AbstractCardPatch.entomb.get(c))
                .collect(Collectors.toList());

        // remove card from draw pile copy.
        // we have to do it in a separate loop, since we cannot remove items from the same list that we are looping in
        entombCards
                .forEach(c -> drawPileCopy.group.remove(c));
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(CardGroup.class, "shuffle");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
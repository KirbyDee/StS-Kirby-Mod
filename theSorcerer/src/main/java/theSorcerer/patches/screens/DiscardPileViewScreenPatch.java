package theSorcerer.patches.screens;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DiscardPileViewScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.cards.SorcererCardTags;

@SpirePatch(clz = DiscardPileViewScreen.class, method = SpirePatch.CLASS)
public class DiscardPileViewScreenPatch {

    @SpirePatch(clz = DiscardPileViewScreen.class, method = "update")
    public static class UpdatePatch {

        private static final Logger LOG = LogManager.getLogger(UpdatePatch.class.getName());

        public static void Postfix(DiscardPileViewScreen self) {
            AbstractPileViewScreenPatch.Postfix(
                    AbstractDungeon.player.discardPile,
                    UpdatePatch::isFlashback,
                    UpdatePatch::removeFromDiscardPileAndAddToHand
            );
        }

        private static boolean isFlashback(AbstractCard card) {
            return card.hasTag(SorcererCardTags.FLASHBACK);
        }

        private static void removeFromDiscardPileAndAddToHand(AbstractCard card) {
            LOG.info("Card " + card.name + " remove from discard pile and add to hand");
            card.unhover();
            card.stopGlowing();
            card.current_x = CardGroup.DISCARD_PILE_X;
            card.current_y = CardGroup.DISCARD_PILE_Y;

            AbstractPileViewScreenPatch.computeDescription(card);

            // TODO does this count as drawing?
            AbstractDungeon.player.hand.addToHand(card);
            AbstractDungeon.player.discardPile.removeCard(card);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
            AbstractDungeon.closeCurrentScreen();
        }
    }
}
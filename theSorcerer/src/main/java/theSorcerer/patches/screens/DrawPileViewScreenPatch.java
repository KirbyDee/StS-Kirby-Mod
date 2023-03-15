package theSorcerer.patches.screens;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DrawPileViewScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCardTags;

@SpirePatch(clz = DrawPileViewScreen.class, method = SpirePatch.CLASS)
public class DrawPileViewScreenPatch {

    private static final Logger LOG = LogManager.getLogger(DrawPileViewScreenPatch.class.getName());

    private static boolean isFuturity(AbstractCard card) {
        return card.hasTag(SorcererCardTags.FUTURITY);
    }

    @SpirePatch(clz = DrawPileViewScreen.class, method = "open")
    public static class OpenPatch {

        public static void Postfix(DrawPileViewScreen self) {
            AbstractPileViewScreenPatch.OpenPatch(
                    AbstractDungeon.player.drawPile,
                    DrawPileViewScreenPatch::isFuturity
            );
        }
    }

    @SpirePatch(clz = DrawPileViewScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(DrawPileViewScreen self) {
            AbstractPileViewScreenPatch.UpdatePatch(
                    AbstractDungeon.player.drawPile,
                    DrawPileViewScreenPatch::isFuturity,
                    UpdatePatch::removeFromDrawPileAndAddToHand
            );
        }

        private static void removeFromDrawPileAndAddToHand(AbstractCard card) {
            LOG.info("Card " + card.name + " remove from draw pile and add to hand");
            card.unhover();
            card.stopGlowing();
            card.current_x = CardGroup.DRAW_PILE_X;
            card.current_y = CardGroup.DRAW_PILE_Y;

            AbstractPileViewScreenPatch.computeDescription(card);

            AbstractDungeon.closeCurrentScreen();
            AbstractDungeon.player.hand.addToHand(card);
            AbstractDungeon.player.drawPile.removeCard(card);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();

            // trigger on futurity
            if (card instanceof DynamicCard) {
                ((DynamicCard) card).triggerOnFuturity();
            }
        }
    }
}
package theSorcerer.patches.screens;

import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.DrawPileViewScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.FuturityMod;

@SpirePatch(clz = DrawPileViewScreen.class, method = SpirePatch.CLASS)
public class DrawPileViewScreenPatch {

    private static final Logger LOG = LogManager.getLogger(DrawPileViewScreenPatch.class.getName());

    private static boolean isFuturity(AbstractCard card) {
        boolean isFuturity = DynamicDungeon.isFuturityCard(card);
        if (!isFuturity && card.isGlowing) {
            card.stopGlowing();
        }
        return isFuturity;
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

            DynamicDungeon.makeCardExhaust(card);
            DynamicDungeon.makeCardEthereal(card);

            AbstractDungeon.closeCurrentScreen();
            AbstractDungeon.player.hand.addToHand(card);
            AbstractDungeon.player.drawPile.removeCard(card);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();

            // trigger on futurity
            // TODOO  move to FuturityMod? but how?
            DynamicDungeon.triggerOnFuturity(card);

            // trigger flash
            card.superFlash();
        }
    }
}
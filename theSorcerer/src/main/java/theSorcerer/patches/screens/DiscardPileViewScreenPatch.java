package theSorcerer.patches.screens;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.DiscardPileViewScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.buff.PastEmbracePower;

@SpirePatch(clz = DiscardPileViewScreen.class, method = SpirePatch.CLASS)
public class DiscardPileViewScreenPatch {

    private static final Logger LOG = LogManager.getLogger(DiscardPileViewScreenPatch.class.getName());

    private static boolean isFlashback(AbstractCard card) {
        return DynamicDungeon.isFlashbackCard(card);
    }

    @SpirePatch(clz = DiscardPileViewScreen.class, method = "update")
    public static class UpdatePatch {

        public static void Postfix(DiscardPileViewScreen self) {
            AbstractPileViewScreenPatch.UpdatePatch(
                    AbstractDungeon.player.discardPile,
                    DiscardPileViewScreenPatch::isFlashback,
                    UpdatePatch::removeFromDiscardPileAndAddToHand
            );
        }

        private static void removeFromDiscardPileAndAddToHand(AbstractCard card) {
            LOG.info("Card " + card.name + " remove from discard pile and add to hand");
            card.unhover();
            card.stopGlowing();
            card.current_x = CardGroup.DISCARD_PILE_X;
            card.current_y = CardGroup.DISCARD_PILE_Y;

            AbstractPileViewScreenPatch.computeDescription(card);

            AbstractDungeon.player.hand.addToHand(card);
            AbstractDungeon.player.discardPile.removeCard(card);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
            AbstractDungeon.closeCurrentScreen();

            // trigger PastEmbracePower
            DynamicDungeon.withPowerDo(AbstractDungeon.player, PastEmbracePower.class, AbstractPower::onSpecificTrigger);

            // trigger on flashback
            DynamicDungeon.triggerOnFlashback(card);

            // trigger flash
            card.superFlash();
        }
    }
}
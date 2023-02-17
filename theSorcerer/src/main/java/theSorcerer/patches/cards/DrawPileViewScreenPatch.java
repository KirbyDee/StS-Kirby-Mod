package theSorcerer.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.DrawPileViewScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCardTags;

@SpirePatch(clz = DrawPileViewScreen.class, method = SpirePatch.CLASS)
public class DrawPileViewScreenPatch {

    @SpirePatch(clz = DrawPileViewScreen.class, method = "update")
    public static class UpdatePatch {

        private static final Logger LOG = LogManager.getLogger(UpdatePatch.class.getName());

        public static void Postfix(DrawPileViewScreen self) {
            if (AbstractDungeon.player.hand.size() > 10) {
                return;
            }

            AbstractDungeon.player.drawPile.group
                    .stream()
                    .filter(UpdatePatch::isFuturity)
                    .filter(UpdatePatch::isHovered)
                    .filter(c -> InputHelper.justClickedLeft)
                    .findAny()
                    .ifPresent(UpdatePatch::removeFromDrawPileAndAddToHand);
        }

        private static boolean isFuturity(AbstractCard card) {
            return card.hasTag(SorcererCardTags.FUTURITY);
        }

        private static boolean isHovered(AbstractCard card) {
            return card.hb.hovered;
        }

        private static void removeFromDrawPileAndAddToHand(AbstractCard card) {
            LOG.info("Card " + card.name + " remove from draw pile and add to hand");
            card.unhover();
            card.current_x = CardGroup.DRAW_PILE_X;
            card.current_y = CardGroup.DRAW_PILE_Y;

            computeDescription(card);

            AbstractDungeon.closeCurrentScreen();
            AbstractDungeon.player.hand.addToHand(card);
            AbstractDungeon.player.drawPile.removeCard(card);
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.applyPowers();
        }

        private static void computeDescription(AbstractCard card) {
            if (!card.exhaust) {
                card.exhaust = true;
                DynamicCard.CardAbility.EXHAUST.addDescription(card);
            }
            if (!card.isEthereal) {
                card.isEthereal = true;
                DynamicCard.CardAbility.ETHEREAL.addDescription(card);
            }
            DynamicCard.CardAbility.FUTURITY.removeDescription(card);
            DynamicCard.CardAbility.FLASHBACK.removeDescription(card);

            card.initializeDescription();
        }
    }
}
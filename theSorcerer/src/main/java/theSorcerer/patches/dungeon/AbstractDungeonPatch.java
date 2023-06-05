package theSorcerer.patches.dungeon;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import theSorcerer.DynamicDungeon;
import theSorcerer.relics.BottledGhost;
import theSorcerer.relics.BottledLife;
import theSorcerer.relics.DynamicRelic;
import theSorcerer.relics.TreeOfLife;

import java.util.function.Predicate;

@SpirePatch(clz = AbstractDungeon.class, method = SpirePatch.CLASS)
public class AbstractDungeonPatch {

    @SpirePatch(clz = AbstractDungeon.class, method = "closeCurrentScreen")
    public static class CloseCurrentScreenPatch {

        public static void Prefix() {
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GAME_DECK_VIEW) {
                stopGlow(AbstractDungeon.player.drawPile, DynamicDungeon::isFuturityCard);
            }
            else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.DISCARD_VIEW) {
                stopGlow(AbstractDungeon.player.discardPile, DynamicDungeon::isFlashbackCard);
            }
        }

        private static void stopGlow(
                final CardGroup cardGroup,
                final Predicate<AbstractCard> filter
        ) {
            cardGroup.group
                    .stream()
                    .filter(filter)
                    .forEach(CloseCurrentScreenPatch::stopGlow);
        }

        private static void stopGlow(final AbstractCard card) {
            if (card.isGlowing) {
                card.stopGlowing();
            }
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "returnRandomScreenlessRelic")
    public static class ReturnRandomScreenlessRelicPatch {

        public static AbstractRelic Postfix(
                AbstractRelic result,
                AbstractRelic.RelicTier tier
        ) {
            if (isScreenRelic(result)) {
                return AbstractDungeon.returnRandomScreenlessRelic(tier);
            }
            return result;
        }

        private static boolean isScreenRelic(final AbstractRelic relic) {
            return DynamicRelic.getID(BottledGhost.class).equals(relic.relicId) ||
                    DynamicRelic.getID(BottledLife.class).equals(relic.relicId);
        }
    }

    @SpirePatch(clz = AbstractDungeon.class, method = "returnRandomNonCampfireRelic")
    public static class ReturnRandomNonCampfireRelicPatch {

        public static AbstractRelic Postfix(
                AbstractRelic result,
                AbstractRelic.RelicTier tier
        ) {
            if (isCampfireRelic(result)) {
                return AbstractDungeon.returnRandomNonCampfireRelic(tier);
            }
            return result;
        }

        private static boolean isCampfireRelic(final AbstractRelic relic) {
            return DynamicRelic.getID(TreeOfLife.class).equals(relic.relicId);
        }
    }
}
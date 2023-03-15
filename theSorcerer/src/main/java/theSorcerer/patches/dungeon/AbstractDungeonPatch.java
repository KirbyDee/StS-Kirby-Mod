package theSorcerer.patches.dungeon;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.cards.SorcererCardTags;

import java.util.function.Predicate;

@SpirePatch(clz = AbstractDungeon.class, method = SpirePatch.CLASS)
public class AbstractDungeonPatch {

    @SpirePatch(clz = AbstractDungeon.class, method = "closeCurrentScreen")
    public static class CloseCurrentScreenPatch {

        public static void Prefix() {
            if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GAME_DECK_VIEW) {
                stopGlow(AbstractDungeon.player.drawPile, card -> card.hasTag(SorcererCardTags.FUTURITY));
            }
            else if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.DISCARD_VIEW) {
                stopGlow(AbstractDungeon.player.discardPile, card -> card.hasTag(SorcererCardTags.FLASHBACK));
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
}
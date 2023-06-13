package theSorcerer.patches.actions.common;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.DiscardAtEndOfTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;

import java.util.Iterator;

@SpirePatch(clz = DiscardAtEndOfTurnAction.class, method = SpirePatch.CLASS)
public class DiscardAtEndOfTurnActionPatch {

    @SpirePatch(clz = DiscardAtEndOfTurnAction.class, method = "update")
    public static class UpdatePatch {

        public static void Prefix(DiscardAtEndOfTurnAction self) {
            Iterator<AbstractCard> c = AbstractDungeon.player.hand.group.iterator();

            while (true) {
                AbstractCard e;
                do {
                    if (!c.hasNext()) {
                        return;
                    }
                    e = c.next();
                } while (!DynamicDungeon.isCopycatCard(e));

                AbstractDungeon.player.limbo.addToTop(e);
                c.remove();
            }
        }
    }
}
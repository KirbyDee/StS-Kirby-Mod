package theSorcerer.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;
import theSorcerer.actions.AutoUseCardAction;

@SpirePatch(clz = CardGroup.class, method = SpirePatch.CLASS)
public class CardGroupPatch {

    @SpirePatch(clz = CardGroup.class, method = "addToHand")
    public static class AddToHandPatch {

        public static void Postfix(CardGroup self, AbstractCard c) {
            if (DynamicDungeon.isAutoCard(c)) {
                AbstractDungeon.actionManager.addToBottom(
                        new AutoUseCardAction(
                                AbstractDungeon.player,
                                c
                        )
                );
            }
        }
    }
}
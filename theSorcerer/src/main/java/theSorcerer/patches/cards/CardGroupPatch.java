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

    // TODO issues at startup
//    @SpirePatch(clz = CardGroup.class, method = "getGroupWithoutBottledCards")
//    public static class GetGroupWithoutBottledCardsPatch {
//
//        public static CardGroup Postfix(CardGroup __retVal, CardGroup self, CardGroup group) {
//            CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
//            for (AbstractCard card : __retVal.group) {
//                if (!AbstractCardPatch.inBottleGhost.get(card) && !AbstractCardPatch.inBottleEnergy.get(card)) {
//                    retVal.addToTop(card);
//                }
//            }
//            return retVal;
//        }
//    }
}
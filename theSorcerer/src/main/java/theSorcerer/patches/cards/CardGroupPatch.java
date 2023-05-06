package theSorcerer.patches.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    @SpirePatch(clz = CardGroup.class, method = "renderMasterDeck")
    public static class RenderMasterDeckPatch {

        public static void Prefix(CardGroup self, SpriteBatch sb) {
            self.group.forEach(CardGroupPatch::initializeDescription);
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "renderMasterDeckExceptOneCard")
    public static class RenderMasterDeckExceptOneCardPatch {

        public static void Prefix(CardGroup self, SpriteBatch sb, AbstractCard card) {
            self.group.stream()
                    .filter(c -> !card.equals(c))
                    .forEach(CardGroupPatch::initializeDescription);
        }
    }

    private static void initializeDescription(final AbstractCard card) {
        if (AbstractCardPatch.inBottleEnergy.get(card)) {
            // need to remove first any ice/fire/arcane which could be in the description
            CardAbility.ICE.removeRawDescription(card);
            CardAbility.FIRE.removeRawDescription(card);
            CardAbility.ARCANE.removeRawDescription(card);

            // add arcane to the description
            CardAbility.ARCANE.addRawDescription(card);

            // update description again with abilities
            DynamicDungeon.updateAbilityDescription(card);
        }
    }
}
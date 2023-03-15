package theSorcerer.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

@SpirePatch(clz = CardGroup.class, method = SpirePatch.CLASS)
public class CardGroupPatch {

    @SpirePatch(clz = CardGroup.class, method = "addToHand")
    public static class AddToHandPatch {

        public static void Postfix(CardGroup self, AbstractCard c) {
            AbstractPlayer player = AbstractDungeon.player;
            if (AbstractCardPatch.abilities.get(c).contains(CardAbility.AUTO)) {
                if (c.hasEnoughEnergy()) {
                    player.useCard(c, AbstractDungeon.getRandomMonster(), c.energyOnUse);
                }
                else {
                    player.hand.moveToExhaustPile(c);
                }
            }
        }
    }
}
package theSorcerer.patches.characters;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.actions.CopycatCardsAction;
import theSorcerer.modifiers.CardModifier;
import theSorcerer.modifiers.CopyCatMod;
import theSorcerer.modifiers.CopyCatNormalMod;
import theSorcerer.modifiers.CopyCatUpgradedMod;

@SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
public class AbstractPlayerPatch {

    public static SpireField<Integer> elementalCardsPlayedPerCombat = new SpireField<>(() -> 0);

    public static SpireField<Integer> arcaneCardsPlayedPerCombat = new SpireField<>(() -> 0);



    @SpirePatch(clz = AbstractPlayer.class, method = "draw", paramtypez={int.class})
    public static class DrawPatch {

        public static void Postfix(
                AbstractPlayer self,
                int numCards
        ) {
            self.hand.group.forEach(DrawPatch::checkCopycat);
        }

        private static void checkCopycat(final AbstractCard card) {
            checkCopycat(card, CopyCatNormalMod.ID);
            checkCopycat(card, CopyCatUpgradedMod.ID);
        }

        private static void checkCopycat(final AbstractCard card, final String modId) {
            CardModifierManager.getModifiers(card, modId)
                    .stream()
                    .filter(CopyCatMod.class::isInstance)
                    .map(CopyCatMod.class::cast)
                    .filter(CopyCatMod::isCopycat)
                    .findAny()
                    .ifPresent(m -> triggerCopyCat(card, m));
        }

        private static void triggerCopyCat(final AbstractCard card, final CopyCatMod mod) {
            mod.setCopycat(false);
            DynamicDungeon.addToBot(
                    new CopycatCardsAction(card)
            );
        }
    }
}
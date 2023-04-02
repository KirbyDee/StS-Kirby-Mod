package theSorcerer.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.debuff.ElementlessPower;

import java.util.ArrayList;
import java.util.List;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class AbstractCardPatch {

    public static SpireField<List<CardAbility>> abilities = new SpireField<>(ArrayList::new);

    private static final PowerStrings ELEMENTLESS_STRINGS = CardCrawlGame.languagePack.getPowerStrings(ElementlessPower.POWER_ID);

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopyPatch {

        public static AbstractCard Postfix(
                AbstractCard result,
                AbstractCard self
        ) {
            result.isEthereal = self.isEthereal;
            result.isInnate = self.isInnate;
            result.retain = self.retain;
            result.exhaust = self.exhaust;
            result.tags = self.tags;
//            AbstractCardPatch.abilities.set(result, AbstractCardPatch.abilities.get(self)); // TODO: upgrade card will not have the same abilities as before?

            return result;
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "canUse")
    public static class CanUsePatch {

        public static boolean Postfix(
                boolean result,
                AbstractCard self,
                AbstractPlayer player,
                AbstractMonster monster
        ) {
            if (!result) {
                return false;
            }
            else if (DynamicDungeon.hasElementless() && DynamicDungeon.isElementCard(self)) {
                self.cantUseMessage = ELEMENTLESS_STRINGS.DESCRIPTIONS[1];
                return false;
            }
            return true;
        }
    }
}
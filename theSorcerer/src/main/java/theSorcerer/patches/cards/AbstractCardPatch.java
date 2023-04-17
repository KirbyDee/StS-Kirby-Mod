package theSorcerer.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.debuff.ElementlessPower;

import java.util.ArrayList;
import java.util.List;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class AbstractCardPatch {

    public static SpireField<List<CardAbility>> abilities = new SpireField<>(ArrayList::new);

    public static SpireField<Boolean> inBottleEnergy = new SpireField<>(() -> false);

    public static SpireField<Boolean> inBottleTombstone = new SpireField<>(() -> false);

    private static final PowerStrings ELEMENTLESS_STRINGS = CardCrawlGame.languagePack.getPowerStrings(ElementlessPower.POWER_ID);

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopyPatch {

        public static AbstractCard Postfix(
                AbstractCard result,
                AbstractCard self
        ) {
            inBottleTombstone.set(result, inBottleTombstone.get(self));
            inBottleEnergy.set(result, inBottleEnergy.get(self));
            if (inBottleEnergy.get(result)) {
                DynamicDungeon.makeCardArcane(result);
            }

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
            else if (DynamicDungeon.isArcaneCard(self) && !DynamicDungeon.isHeatedOrChillder()) {
                return false;
            }
            else if (DynamicDungeon.isElementCard(self) && DynamicDungeon.hasElementless()) {
                self.cantUseMessage = ELEMENTLESS_STRINGS.DESCRIPTIONS[1];
                return false;
            }
            return true;
        }
    }
}
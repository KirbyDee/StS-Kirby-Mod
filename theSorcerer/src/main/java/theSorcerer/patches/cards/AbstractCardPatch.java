package theSorcerer.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.DynamicPower;
import theSorcerer.powers.debuff.ElementlessPower;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class AbstractCardPatch {

    public static SpireField<Boolean> inBottleEnergy = new SpireField<>(() -> false);

    public static SpireField<Boolean> inBottleGhost = new SpireField<>(() -> false);

    public static SpireField<Boolean> fire = new SpireField<>(() -> false);

    public static SpireField<Boolean> ice = new SpireField<>(() -> false);

    public static SpireField<Boolean> arcane = new SpireField<>(() -> false);

    public static SpireField<Boolean> futurity = new SpireField<>(() -> false);

    public static SpireField<Boolean> flashback = new SpireField<>(() -> false);

    private static final PowerStrings ELEMENTLESS_STRINGS = CardCrawlGame.languagePack.getPowerStrings(DynamicPower.getID(ElementlessPower.class));

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopyPatch {

        public static AbstractCard Postfix(
                AbstractCard result,
                AbstractCard self
        ) {
            inBottleGhost.set(result, inBottleGhost.get(self));
            inBottleEnergy.set(result, inBottleEnergy.get(self));
            arcane.set(result, arcane.get(self));
            if (inBottleEnergy.get(result) || arcane.get(result)) {
                DynamicDungeon.makeCardArcane(result);
            }
            fire.set(result, fire.get(self));
            if (fire.get(result)) {
                DynamicDungeon.makeCardFire(result);
            }
            ice.set(result, ice.get(self));
            if (ice.get(result)) {
                DynamicDungeon.makeCardIce(result);
            }
            flashback.set(result, flashback.get(self));
            if (flashback.get(result)) {
                DynamicDungeon.makeCardFlashback(result);
            }
            futurity.set(result, futurity.get(self));
            if (futurity.get(result)) {
                DynamicDungeon.makeCardFuturity(result);
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
            else if (DynamicDungeon.isElementCard(self) && DynamicDungeon.hasElementless()) {
                self.cantUseMessage = ELEMENTLESS_STRINGS.DESCRIPTIONS[1];
                return false;
            }
            else if (DynamicDungeon.isArcaneCard(self) && !DynamicDungeon.canPlayArcane()) {
                self.cantUseMessage = ELEMENTLESS_STRINGS.DESCRIPTIONS[2];
                return false;
            }
            return true;
        }
    }
}
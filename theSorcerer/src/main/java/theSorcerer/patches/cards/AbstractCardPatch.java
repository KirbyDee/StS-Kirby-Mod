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

import java.util.HashSet;
import java.util.Set;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class AbstractCardPatch {

    public static SpireField<Set<CardAbility>> abilities = new SpireField<>(HashSet::new);

    public static SpireField<Boolean> inBottleEnergy = new SpireField<>(() -> false);

    public static SpireField<Boolean> inBottleGhost = new SpireField<>(() -> false);

    public static SpireField<Boolean> hasBeenMadeFire = new SpireField<>(() -> false);

    public static SpireField<Boolean> hasBeenMadeIce = new SpireField<>(() -> false);

    public static SpireField<Boolean> hasBeenMadeArcane = new SpireField<>(() -> false);

    public static SpireField<Boolean> hasBeenMadeFuturity = new SpireField<>(() -> false);

    public static SpireField<Boolean> hasBeenMadeFlashback = new SpireField<>(() -> false);

    private static final PowerStrings ELEMENTLESS_STRINGS = CardCrawlGame.languagePack.getPowerStrings(DynamicPower.getID(ElementlessPower.class));

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopyPatch {

        public static AbstractCard Postfix(
                AbstractCard result,
                AbstractCard self
        ) {
            // permanent ability changes
            inBottleGhost.set(result, inBottleGhost.get(self));
            inBottleEnergy.set(result, inBottleEnergy.get(self));
            hasBeenMadeArcane.set(result, hasBeenMadeArcane.get(self));
            hasBeenMadeFire.set(result, hasBeenMadeFire.get(self));
            hasBeenMadeIce.set(result, hasBeenMadeIce.get(self));
            hasBeenMadeFuturity.set(result, hasBeenMadeFuturity.get(self));
            hasBeenMadeFlashback.set(result, hasBeenMadeFlashback.get(self));
            if (inBottleEnergy.get(result) || hasBeenMadeArcane.get(result)) {
                DynamicDungeon.makeCardArcane(result);
            }
            if (hasBeenMadeFire.get(result)) {
                DynamicDungeon.makeCardFire(result);
            }
            if (hasBeenMadeIce.get(result)) {
                DynamicDungeon.makeCardIce(result);
            }
            if (hasBeenMadeFuturity.get(result)) {
                DynamicDungeon.makeCardFuturity(result);
            }
            if (hasBeenMadeFlashback.get(result)) {
                DynamicDungeon.makeCardFlashback(result);
            }
            DynamicDungeon.updateAbilityDescription(result);
            // TODO: upgrade screen does not show "temporary" ability changes.. how do we do that?

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
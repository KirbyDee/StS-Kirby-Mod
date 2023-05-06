package theSorcerer.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.DynamicPower;
import theSorcerer.powers.debuff.ElementlessPower;

import java.util.HashSet;
import java.util.Set;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class AbstractCardPatch {

    public static SpireField<Set<CardAbility>> abilities = new SpireField<>(HashSet::new);

    public static SpireField<Set<CardAbility>> abilitiesPerCombat = new SpireField<>(HashSet::new);

    public static SpireField<Boolean> inBottleEnergy = new SpireField<>(() -> false);

    public static SpireField<Boolean> inBottleGhost = new SpireField<>(() -> false);

    private static final PowerStrings ELEMENTLESS_STRINGS = CardCrawlGame.languagePack.getPowerStrings(DynamicPower.getID(ElementlessPower.class));

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopyPatch {

        public static AbstractCard Postfix(
                AbstractCard result,
                AbstractCard self
        ) {
            inBottleGhost.set(result, inBottleGhost.get(self));
            inBottleEnergy.set(result, inBottleEnergy.get(self));
            if (inBottleEnergy.get(result)) {
                DynamicDungeon.makeCardArcane(result, true);
            }
            abilities.set(result, abilities.get(self));
            if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                abilitiesPerCombat.set(result, abilitiesPerCombat.get(self));
            }
            else {
                abilitiesPerCombat.get(result).addAll(new HashSet<>(abilities.get(self)));
            }
            DynamicDungeon.updateAbilityDescription(result);
            // TODO: abilitiesPerCombat are not copied, only abilities... how do we want to show the correct element in the upgrade screen

            // TODOO: do the same with fire/ice that can be given on rest site due to event

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
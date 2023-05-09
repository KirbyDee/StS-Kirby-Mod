package theSorcerer.patches.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.DynamicDungeon;
import theSorcerer.patches.cards.CardAbility;
import theSorcerer.powers.DynamicPower;
import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.buff.HeatedPower;
import theSorcerer.relics.DynamicRelic;
import theSorcerer.relics.ElementalMaster;
import theSorcerer.relics.ProtectingGloves;

@SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
public class AbstractPlayerPatch {

    private static final Logger LOG = LogManager.getLogger(AbstractPlayerPatch.class.getName());

    public static SpireField<Integer> elementalCardsPlayedPerCombat = new SpireField<>(() -> 0);

    public static SpireField<Integer> arcaneCardsPlayedPerCombat = new SpireField<>(() -> 0);

    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class UseCardPatch {

        public static void Postfix(AbstractPlayer self, AbstractCard card, AbstractMonster monster, int energyOnUse) {
            LOG.debug("Card: " + self.name + " - " + card.tags);

            // elementless
            if (hasElementlessPower(self, card)) {
                return;
            }

            // elemental card played +1
            if (DynamicDungeon.isElementCard(card)) {
                elementalCardsPlayedPerCombat.set(self, elementalCardsPlayedPerCombat.get(self) + 1);
            }
            if (DynamicDungeon.isArcaneCard(card)) {
                arcaneCardsPlayedPerCombat.set(self, arcaneCardsPlayedPerCombat.get(self) + 1);
            }

            // invalid switch
            if (doesInvalidElementSwitch(self, card)) {
                return;
            }

            // apply element power
            applyElementPower(card);
        }

        private static void applyElementPower(final AbstractCard card) {
            if (DynamicDungeon.isArcaneCard(card)) {
                DynamicDungeon.applyPresenceOfMind();
                if (DynamicDungeon.isHeated()) {
                    DynamicDungeon.applyHeated(card.costForTurn);
                }
                else if (DynamicDungeon.isChilled()) {
                    DynamicDungeon.applyChilled(card.costForTurn);
                }
                else if (DynamicDungeon.hasRelic(ProtectingGloves.class)) {
                    DynamicDungeon.triggerRelic(AbstractDungeon.player.getRelic(DynamicRelic.getID(ProtectingGloves.class)));
                }
            }
            else if (DynamicDungeon.isFireCard(card)) {
                DynamicDungeon.applyHeated(card.costForTurn);
            }
            else if (DynamicDungeon.isIceCard(card)) {
                DynamicDungeon.applyChilled(card.costForTurn);
            }
        }

        private static boolean hasElementlessPower(AbstractPlayer self, AbstractCard card) {
            if (DynamicDungeon.hasElementless()) {
                if (DynamicDungeon.isElementCard(card)) {
                    DynamicDungeon.flashElementlessRelic();
                    LOG.debug("Player cannot gain any element due to Elementless debuff -> NOP");
                }
                return true;
            }
            return false;
        }

        private static boolean doesInvalidElementSwitch(AbstractPlayer self, AbstractCard card) {
            return doesInvalidElementSwitch(self, card, DynamicPower.getID(HeatedPower.class), CardAbility.ICE) ||
                    doesInvalidElementSwitch(self, card, DynamicPower.getID(ChilledPower.class), CardAbility.FIRE);
        }

        private static boolean doesInvalidElementSwitch(
                final AbstractPlayer self,
                final AbstractCard card,
                final String selfHasPowerId,
                final CardAbility cardAbility
        ) {
            if (self.hasPower(selfHasPowerId) && DynamicDungeon.cardHasAbility(card, cardAbility)) {
                if (self.hasRelic(DynamicRelic.getID(ElementalMaster.class))) {
                    LOG.debug("Player has ElementalMaster relic, so possible to switch elements");
                    DynamicDungeon.triggerRelic(self.getRelic(DynamicRelic.getID(ElementalMaster.class)));
                    return false;
                }
                else {
                    LOG.debug("Player cannot gain any element due to element switch -> NOP");
                    return true;
                }
            }
            return false;
        }
    }
}
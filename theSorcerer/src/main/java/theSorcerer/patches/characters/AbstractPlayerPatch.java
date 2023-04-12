package theSorcerer.patches.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.buff.HeatedPower;
import theSorcerer.relics.ElementalMaster;

@SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
public class AbstractPlayerPatch {

    private static final Logger LOG = LogManager.getLogger(AbstractPlayerPatch.class.getName());

    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class UseCardPatch {

        public static void Postfix(AbstractPlayer self, AbstractCard card, AbstractMonster monster, int energyOnUse) {
            LOG.debug("Card: " + self.name + " - " + card.tags);

            // invalid cast
            if (hasNotEnoughCost(card) || hasElementlessPower(self, card)) {
                return;
            }

            // element switch check
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
            }
            else if (DynamicDungeon.isFireCard(card)) {
                DynamicDungeon.applyHeated(card.costForTurn);
            }
            else if (DynamicDungeon.isIceCard(card)) {
                DynamicDungeon.applyChilled(card.costForTurn);
            }
        }

        private static boolean hasNotEnoughCost(AbstractCard card) {
            if (card.costForTurn <= 0) {
                LOG.debug("Cost of card is 0 or less -> NOP");
                return true;
            }
            return false;
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
            return doesInvalidElementSwitch(self, card, HeatedPower.POWER_ID, SorcererCardTags.ICE) ||
                    doesInvalidElementSwitch(self, card, ChilledPower.POWER_ID, SorcererCardTags.FIRE);
        }

        private static boolean doesInvalidElementSwitch(
                final AbstractPlayer self,
                final AbstractCard card,
                final String selfHasPowerId,
                final AbstractCard.CardTags cardHasTag
        ) {
            if (self.hasPower(selfHasPowerId) && card.hasTag(cardHasTag)) {
                if (self.hasRelic(ElementalMaster.ID)) {
                    LOG.debug("Player has ElementalMaster relic, so possible to switch elements");
                    self.getRelic(ElementalMaster.ID).flash();
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
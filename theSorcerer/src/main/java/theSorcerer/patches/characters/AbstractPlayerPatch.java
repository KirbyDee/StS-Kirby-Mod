package theSorcerer.patches.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.powers.buff.ElementAffinityPower;
import theSorcerer.powers.buff.FireAffinityPower;
import theSorcerer.powers.buff.IceAffinityPower;
import theSorcerer.powers.debuff.ElementlessPower;
import theSorcerer.relics.ElementalMaster;

@SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
public class AbstractPlayerPatch {

    private static final Logger LOG = LogManager.getLogger(AbstractPlayerPatch.class.getName());

    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class UseCardPatch {

        public static void Prefix(AbstractPlayer self, AbstractCard card, AbstractMonster monster, int energyOnUse) {
            if (hasNotEnoughCost(card) || hasElementlessPower(self, card) || doesInvalidElementSwitch(self, card)) {
                return;
            }

            LOG.debug("Card: " + self.name + " - " + card.tags);
            if (card.hasTag(SorcererCardTags.FIRE)) {
                increaseElementAffinity(card, self, new FireAffinityPower(self, card.costForTurn));
            }
            else if (card.hasTag(SorcererCardTags.ICE)) {
                increaseElementAffinity(card, self, new IceAffinityPower(self, card.costForTurn));
            }
        }

        private static void increaseElementAffinity(AbstractCard card, AbstractPlayer player, ElementAffinityPower<?> elementAffinityPower) {
            LOG.info("Increase " + elementAffinityPower.ID);
            AbstractDungeon.actionManager.addToTop(
                    new ApplyPowerAction(
                            player,
                            player,
                            elementAffinityPower,
                            card.costForTurn
                    )
            );
        }

        private static boolean hasNotEnoughCost(AbstractCard card) {
            if (card.costForTurn <= 0) {
                LOG.debug("Cost of card is 0 or less -> NOP");
                return true;
            }
            return false;
        }

        private static boolean hasElementlessPower(AbstractPlayer self, AbstractCard card) {
            if (self.hasPower(ElementlessPower.POWER_ID)) {
                if (card.hasTag(SorcererCardTags.FIRE) || card.hasTag(SorcererCardTags.ICE)) {
                    self.getPower(ElementlessPower.POWER_ID).flash();
                    LOG.debug("Player cannot gain any element affinity due to Elementless debuff -> NOP");
                }
                return true;
            }
            return false;
        }

        private static boolean doesInvalidElementSwitch(AbstractPlayer self, AbstractCard card) {
            return doesInvalidElementSwitch(self, card, FireAffinityPower.POWER_ID, SorcererCardTags.ICE) ||
                    doesInvalidElementSwitch(self, card, IceAffinityPower.POWER_ID, SorcererCardTags.FIRE);
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
                    LOG.debug("Player cannot gain any element affinity due to element switch -> NOP");
                    return true;
                }
            }
            return false;
        }
    }
}
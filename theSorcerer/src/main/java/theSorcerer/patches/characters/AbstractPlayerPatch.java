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

@SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
public class AbstractPlayerPatch {

    private static final Logger LOG = LogManager.getLogger(AbstractPlayerPatch.class.getName());

    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class UseCardPatch {

        public static void Prefix(AbstractPlayer self, AbstractCard card, AbstractMonster monster, int energyOnUse) {
            if (card.costForTurn <= 0) {
                LOG.debug("Cost of card is 0 or less -> NOP");
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
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(
                            player,
                            player,
                            elementAffinityPower,
                            card.costForTurn
                    )
            );
        }
    }
}
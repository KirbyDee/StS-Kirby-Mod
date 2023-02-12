package theSorcerer.patches.cards;

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

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class ElementAffinityPatch {

    @SpirePatch(clz = AbstractCard.class, method = "onPlayCard")
    public static class OnPlayCardPatch {

        private static final Logger LOG = LogManager.getLogger(OnPlayCardPatch.class.getName());

        public static void Postfix(AbstractCard self, AbstractCard c, AbstractMonster m) {
            // cannot use "use" method, since it is abstract..., so we use "onPlayCard",
            // which is triggered for every card in the deck every time a card is played.
            // So we have to first check that the card played is the one currently checking.
            if (self != c) {
                LOG.debug("Checked cards are not the same -> NOP");
                return;
            }

            AbstractPlayer player = AbstractDungeon.player;
            LOG.info("Card: " + self.name + " - " + self.tags);
            if (self.hasTag(SorcererCardTags.FIRE)) {
                increaseElementAffinity(self, player, new FireAffinityPower(player, self.cost));
            }
            else if (self.hasTag(SorcererCardTags.ICE)) {
                increaseElementAffinity(self, player, new IceAffinityPower(player, self.cost));
            }
        }

        private static void increaseElementAffinity(AbstractCard self, AbstractPlayer p, ElementAffinityPower<?> elementAffinityPower) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(
                            p,
                            p,
                            elementAffinityPower,
                            self.cost
                    )
            );
        }
    }

}
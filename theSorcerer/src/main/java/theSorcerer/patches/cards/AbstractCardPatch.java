package theSorcerer.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
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

import java.util.ArrayList;
import java.util.List;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class AbstractCardPatch {

    private static final Logger LOG = LogManager.getLogger(AbstractCardPatch.class.getName());

    public static SpireField<Boolean> hasInitializedBaseRawDescription = new SpireField<>(() -> false);

    public static SpireField<String> baseRawDescription = new SpireField<>(() -> "");

    public static SpireField<Boolean> flashback = new SpireField<>(() -> false);

    public static SpireField<Boolean> futurity = new SpireField<>(() -> false);

    public static SpireField<Boolean> unplayable = new SpireField<>(() -> false);

    public static SpireField<Boolean> entomb = new SpireField<>(() -> false);

    public static SpireField<List<CardAbility>> abilities = new SpireField<>(ArrayList::new);

    @SpirePatch(clz = AbstractCard.class, method = "initializeDescription")
    public static class InitializeDescriptionPatch {

        public static void Prefix(AbstractCard self) {
            if (!AbstractCardPatch.hasInitializedBaseRawDescription.get(self)) {
                AbstractCardPatch.hasInitializedBaseRawDescription.set(self, true);
                AbstractCardPatch.baseRawDescription.set(self, self.rawDescription);
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopyPatch {

        public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
            result.rawDescription = self.rawDescription;
            result.description = self.description;
            result.keywords = self.keywords;
            result.isEthereal = self.isEthereal;
            result.isInnate = self.isInnate;
            result.retain = self.retain;
            result.exhaust = self.exhaust;
            result.tags = self.tags;
            AbstractCardPatch.baseRawDescription.set(result, AbstractCardPatch.baseRawDescription.get(self));
            AbstractCardPatch.hasInitializedBaseRawDescription.set(result, AbstractCardPatch.hasInitializedBaseRawDescription.get(self));
            AbstractCardPatch.unplayable.set(result, AbstractCardPatch.unplayable.get(self));
            AbstractCardPatch.flashback.set(result, AbstractCardPatch.flashback.get(self));
            AbstractCardPatch.futurity.set(result, AbstractCardPatch.futurity.get(self));
            AbstractCardPatch.entomb.set(result, AbstractCardPatch.entomb.get(self));
            AbstractCardPatch.abilities.set(result, AbstractCardPatch.abilities.get(self));

            return result;
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "onPlayCard")
    public static class OnPlayCardPatch {

        public static void Postfix(AbstractCard self, AbstractCard c, AbstractMonster m) {
            // cannot use "use" method, since it is abstract..., so we use "onPlayCard",
            // which is triggered for every card in the deck every time a card is played.
            // So we have to first check that the card played is the one currently checking.
            if (self != c) {
                LOG.debug("Checked cards are not the same -> NOP");
                return;
            }

            elementAffinityPatch(self, c);
        }

        private static void elementAffinityPatch(AbstractCard self, AbstractCard c) {
            if (c.costForTurn <= 0) {
                LOG.debug("Cost of card is 0 or less -> NOP");
                return;
            }

            AbstractPlayer player = AbstractDungeon.player;
            LOG.debug("Card: " + self.name + " - " + self.tags);
            if (self.hasTag(SorcererCardTags.FIRE)) {
                increaseElementAffinity(self, player, new FireAffinityPower(player, self.costForTurn));
            }
            else if (self.hasTag(SorcererCardTags.ICE)) {
                increaseElementAffinity(self, player, new IceAffinityPower(player, self.costForTurn));
            }
        }

        private static void increaseElementAffinity(AbstractCard self, AbstractPlayer p, ElementAffinityPower<?> elementAffinityPower) {
            LOG.info("Increase " + elementAffinityPower.ID);
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(
                            p,
                            p,
                            elementAffinityPower,
                            self.costForTurn
                    )
            );
        }
    }
}
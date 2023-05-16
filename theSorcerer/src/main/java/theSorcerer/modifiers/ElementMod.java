package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.DynamicDungeon;
import theSorcerer.patches.characters.AbstractPlayerPatch;
import theSorcerer.relics.DynamicRelic;
import theSorcerer.relics.ElementalMaster;

public abstract class ElementMod extends AbstractCardModifier {

    private static final Logger LOG = LogManager.getLogger(ElementMod.class.getName());

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return identifier(card) + " NL " + rawDescription;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        // element card played
        AbstractPlayerPatch.elementalCardsPlayedPerCombat.set(AbstractDungeon.player, AbstractPlayerPatch.elementalCardsPlayedPerCombat.get(AbstractDungeon.player) + 1);

        // invalid switch check
        if (doesInvalidElementSwitch()) {
            return;
        }

        // apply element power
        applyElementPower(card);
    }

    private boolean doesInvalidElementSwitch() {
        if (checkInvalidSwitch()) {
            if (AbstractDungeon.player.hasRelic(DynamicRelic.getID(ElementalMaster.class))) {
                LOG.debug("Player has ElementalMaster relic, so possible to switch elements");
                DynamicDungeon.triggerRelic(AbstractDungeon.player.getRelic(DynamicRelic.getID(ElementalMaster.class)));
                return false;
            }
            else {
                LOG.debug("Player cannot gain any element due to element switch -> NOP");
                return true;
            }
        }
        return false;
    }

    protected abstract boolean checkInvalidSwitch();

    protected abstract void applyElementPower(final AbstractCard card);
}

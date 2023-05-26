package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.patches.characters.AbstractPlayerPatch;

public abstract class ElementMod extends AbstractCardModifier {

    private static final Logger LOG = LogManager.getLogger(ElementMod.class.getName());

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return identifier(card) + ". NL " + rawDescription;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        // element card played
        AbstractPlayerPatch.elementalCardsPlayedPerCombat.set(AbstractDungeon.player, AbstractPlayerPatch.elementalCardsPlayedPerCombat.get(AbstractDungeon.player) + 1);

        // apply element power
        int amount = card.costForTurn;
        if (amount == -1) { // X
            amount = card.energyOnUse;
        }
        if (amount <= 0) {
            amount = 0;
        }
        applyElementPower(amount);
    }

    protected abstract void applyElementPower(final int amount);
}

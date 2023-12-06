package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;
import theSorcerer.patches.characters.AbstractPlayerPatch;

public abstract class ElementMod extends AbstractCardModifier {

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return identifier(card) + ". NL " + rawDescription;
    }

    @Override
    public Color getGlow(AbstractCard card) {
        return !DynamicDungeon.hasElementless() ?
                getElementColor(card) :
                null;
    }

    protected abstract Color getElementColor(final AbstractCard card);

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

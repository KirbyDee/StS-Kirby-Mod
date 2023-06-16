package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;
import theSorcerer.patches.characters.AbstractPlayerPatch;
import theSorcerer.relics.DynamicRelic;
import theSorcerer.relics.ProtectingGloves;

public class ArcaneMod extends ElementMod {

    public static final String ID = "thesorcerer:Arcane";

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ArcaneMod();
    }

    @Override
    public Color getElementColor(AbstractCard card) {
        if (DynamicDungeon.isHeated()) {
            return Color.SCARLET.cpy();
        }
        else if (DynamicDungeon.isChilled()) {
            return Color.NAVY.cpy();
        }
        return Color.WHITE.cpy();
    }

    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.removeModifiersById(card, FireMod.ID, true);
        CardModifierManager.removeModifiersById(card, IceMod.ID, true);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        super.onUse(card, target, action);
        AbstractPlayerPatch.arcaneCardsPlayedPerCombat.set(AbstractDungeon.player, AbstractPlayerPatch.arcaneCardsPlayedPerCombat.get(AbstractDungeon.player) + 1);
        DynamicDungeon.applyPresenceOfMind();
        if (!DynamicDungeon.isHeatedOrChillder() && DynamicDungeon.hasRelic(ProtectingGloves.class)) {
            DynamicDungeon.triggerRelic(AbstractDungeon.player.getRelic(DynamicRelic.getID(ProtectingGloves.class)));
        }
    }

    @Override
    protected void applyElementPower(final int amount) {
        if (DynamicDungeon.isHeated()) {
            DynamicDungeon.applyHeated(amount);
        }
        else if (DynamicDungeon.isChilled()) {
            DynamicDungeon.applyChilled(amount);
        }
    }
}

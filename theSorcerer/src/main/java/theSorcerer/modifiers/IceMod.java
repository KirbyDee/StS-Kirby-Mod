package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.ice.ArcticShell;

public class IceMod extends ElementMod {

    public static final String ID = "thesorcerer:Ice";

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new IceMod();
    }

    @Override
    public Color getElementColor(AbstractCard card) {
        if (card instanceof ArcticShell && ((ArcticShell) card).lastCardPlayedIsFire) {
            return Color.GOLD.cpy();
        }
        return Color.NAVY.cpy();
    }

    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.removeModifiersById(card, FireMod.ID, true);
        CardModifierManager.removeModifiersById(card, ArcaneMod.ID, true);
    }

    @Override
    protected void applyElementPower(final int amount) {
        DynamicDungeon.applyChilled(amount);
    }
}

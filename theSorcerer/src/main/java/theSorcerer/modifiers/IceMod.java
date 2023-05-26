package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.DynamicDungeon;

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


    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.removeModifiersById(card, FireMod.ID, true);
        CardModifierManager.removeModifiersById(card, ArcaneMod.ID, true);
    }

    @Override
    protected void applyElementPower(final int amount) {
        DynamicDungeon.applyChilled(amount);
    }
}

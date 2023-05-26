package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.DynamicDungeon;

public class FireMod extends ElementMod {

    public static final String ID = "thesorcerer:Fire";

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FireMod();
    }


    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.removeModifiersById(card, ArcaneMod.ID, true);
        CardModifierManager.removeModifiersById(card, IceMod.ID, true);
    }

    @Override
    protected void applyElementPower(final int amount) {
        DynamicDungeon.applyHeated(amount);
    }
}

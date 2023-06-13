package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CopyCatMod extends AbstractCardModifier {

    public static final String ID = "thesorcerer:Copycat";

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.removeModifiersById(card, RetainMod.ID, true);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CopyCatMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return ID + (card.upgraded ? "+" : "") + ". NL " + rawDescription;
    }
}

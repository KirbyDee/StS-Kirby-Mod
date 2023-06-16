package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class CopyCatNormalMod extends CopyCatMod {

    public static final String ID = "thesorcerer:Copycat";

    private static final String COPYCAT_DESCRIPTION =  ID + ". NL ";

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        CardModifierManager.removeModifiersById(card, CopyCatUpgradedMod.ID, true);
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new CopyCatNormalMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return COPYCAT_DESCRIPTION + rawDescription;
    }
}

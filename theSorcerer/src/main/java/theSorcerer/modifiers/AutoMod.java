package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AutoMod extends AbstractCardModifier {

    public static final String ID = "thesorcerer:Auto";

    private static final String AUTO_DESCRIPTION =  ID + ". NL ";

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new AutoMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return AUTO_DESCRIPTION + rawDescription;
    }

}

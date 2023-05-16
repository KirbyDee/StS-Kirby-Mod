package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class FuturityMod extends AbstractCardModifier {

    public static final String ID = "thesorcerer:Futurity";

    private static final String FUTURITY_DESCRIPTION = " NL " + ID;

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FuturityMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + FUTURITY_DESCRIPTION;
    }

}

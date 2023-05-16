package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class EntombMod extends AbstractCardModifier {

    public static final String ID = "thesorcerer:Entomb";

    private static final String ENTOMB_DESCRIPTION = ID + " NL ";

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EntombMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return ENTOMB_DESCRIPTION + rawDescription;
    }

}

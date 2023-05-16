package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class UnplayableMod extends AbstractCardModifier {

    public static final String ID = "Unplayable";

    private static final String AUTO_DESCRIPTION =  ID + " NL ";

    @Override
    public boolean canPlayCard(AbstractCard card) {
        return false;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new UnplayableMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return AUTO_DESCRIPTION + rawDescription;
    }

}

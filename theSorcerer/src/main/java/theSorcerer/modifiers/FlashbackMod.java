package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class FlashbackMod extends AbstractCardModifier {

    public static final String ID = "thesorcerer:Flashback";

    private static final String FLASHBACK_DESCRIPTION = " NL " + ID;

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FlashbackMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + FLASHBACK_DESCRIPTION;
    }

}

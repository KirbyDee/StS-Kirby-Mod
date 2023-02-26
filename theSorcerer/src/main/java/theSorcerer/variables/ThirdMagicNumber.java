package theSorcerer.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.cards.DynamicCard;

import static theSorcerer.KirbyDeeMod.makeID;

public class ThirdMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return makeID("MMM");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((DynamicCard) card).isThirdMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((DynamicCard) card).thirdMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((DynamicCard) card).baseThirdMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((DynamicCard) card).upgradedThirdMagicNumber;
    }
}
package theSorcerer.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.cards.DynamicCard;

import static theSorcerer.KirbyDeeMod.makeID;

public class SecondMagicNumber extends DynamicVariable {

    @Override
    public String key() {
        return makeID("M2");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((DynamicCard) card).isSecondMagicNumberModified;
    }

    @Override
    public int value(AbstractCard card) {
        return ((DynamicCard) card).secondMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((DynamicCard) card).baseSecondMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((DynamicCard) card).upgradedSecondMagicNumber;
    }
}
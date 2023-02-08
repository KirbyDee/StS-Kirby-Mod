package theSorcerer.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static theSorcerer.KirbyDeeMod.makeID;

public class CostVariable extends DynamicVariable {
    
    @Override
    public String key() {
        return makeID("COST");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return card.isCostModified;
    }

    @Override
    public int value(AbstractCard card) {
        return card.cost;
    }
    
    @Override
    public int baseValue(AbstractCard card) {
        return card.cost;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
       return false;
    }
}
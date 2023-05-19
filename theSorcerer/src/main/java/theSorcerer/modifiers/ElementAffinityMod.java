package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.interfaces.AlternateCardCostModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.DynamicDungeon;

public class ElementAffinityMod extends AbstractCardModifier implements AlternateCardCostModifier {

    public static final String ID = "thesorcerer:Elementcost";

    private static final String ELEMENTCOST_DESCRIPTION = ID + ". NL ";

    @Override
    public int getAlternateResource(AbstractCard card) {
        return DynamicDungeon.getElementAmount();
    }

    @Override
    public boolean prioritizeAlternateCost(AbstractCard card) {
        return true;
    }

    @Override
    public boolean canSplitCost(AbstractCard card) {
        return true;
    }

    @Override
    public int spendAlternateCost(AbstractCard card, int costToSpend) {
        int resource = getAlternateResource(card);
        if (resource > costToSpend) {
            DynamicDungeon.loseElements(costToSpend);
            costToSpend = 0;
        }
        else if (resource > 0) {
            DynamicDungeon.loseElements(resource);
            costToSpend -= resource;
        }
        return costToSpend;
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ElementAffinityMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return ELEMENTCOST_DESCRIPTION + rawDescription;
    }
}

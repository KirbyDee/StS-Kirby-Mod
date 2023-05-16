package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.interfaces.AlternateCardCostModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.DynamicDungeon;

public class ElementAffinityMod extends AbstractCardModifier implements AlternateCardCostModifier {

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
    public AbstractCardModifier makeCopy() {
        return new ElementAffinityMod();
    }
}

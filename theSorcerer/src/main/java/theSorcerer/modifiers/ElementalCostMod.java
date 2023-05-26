package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.interfaces.AlternateCardCostModifier;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;
import theSorcerer.DynamicDungeon;

import static theSorcerer.modifiers.CardModifier.ELEMENTCOST;

public class ElementalCostMod extends AbstractCardModifier implements AlternateCardCostModifier {

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

    // TODOO: use patch to set element amount and use it in use of the card
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
    public int setXCostLimit(AbstractCard card) {
        return getAlternateResource(card);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ElementalCostMod();
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return ELEMENTCOST_DESCRIPTION + rawDescription;
    }

    @SpirePatch(
            clz = AbstractPlayer.class,
            method = "useCard"
    )
    public static class AbstractPlayerUseCardPatch {

        @SpireInsertPatch(locator = Locator.class)
        public static void initializeDeckPatch(
                AbstractPlayer self,
                AbstractCard c,
                AbstractMonster monster,
                int energyOnUse
        ) {
            // X-cost cards need to also use energy as well as elemental
            if (DynamicDungeon.cardHasModifier(c, ELEMENTCOST) && c.costForTurn == -1) {
                c.costForTurn = c.energyOnUse;
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "costForTurn");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}

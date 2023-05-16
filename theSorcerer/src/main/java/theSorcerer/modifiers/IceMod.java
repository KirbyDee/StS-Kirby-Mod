package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.powers.buff.HeatedPower;

public class IceMod extends ElementMod {

    public static final String ID = "thesorcerer:Ice";

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new IceMod();
    }


    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.removeModifiersById(card, FireMod.ID, true);
        CardModifierManager.removeModifiersById(card, ArcaneMod.ID, true);
    }

    @Override
    protected boolean checkInvalidSwitch() {
        return DynamicDungeon.hasPower(AbstractDungeon.player, HeatedPower.class);
    }

    @Override
    protected void applyElementPower(AbstractCard card) {
        DynamicDungeon.applyChilled(card.costForTurn);
    }
}

package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.buff.ChilledPower;

public class FireMod extends ElementMod {

    public static final String ID = "thesorcerer:Fire";

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FireMod();
    }


    public void onInitialApplication(AbstractCard card) {
        CardModifierManager.removeModifiersById(card, ArcaneMod.ID, true);
        CardModifierManager.removeModifiersById(card, IceMod.ID, true);
    }

    @Override
    protected boolean checkInvalidSwitch() {
        return DynamicDungeon.hasPower(AbstractDungeon.player, ChilledPower.class);
    }

    @Override
    protected void applyElementPower(AbstractCard card) {
        DynamicDungeon.applyHeated(card.costForTurn);
    }
}

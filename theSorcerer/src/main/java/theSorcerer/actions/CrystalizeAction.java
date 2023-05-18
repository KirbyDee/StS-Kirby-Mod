package theSorcerer.actions;

import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;

public class CrystalizeAction extends ElementmorphoseAction {

    public CrystalizeAction() {
        super(
                CardModifier.ICE,
                DynamicDungeon::makeCardIce
        );
    }
}
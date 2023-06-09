package theSorcerer.actions;

import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;

public class CrystalizeAction extends ElementmorphoseAction {

    public CrystalizeAction(
            boolean upgraded
    ) {
        super(
                CardModifier.ICE,
                DynamicDungeon::makeCardIce,
                upgraded
        );
    }
}
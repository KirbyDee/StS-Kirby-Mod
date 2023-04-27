package theSorcerer.actions;

import theSorcerer.DynamicDungeon;
import theSorcerer.patches.cards.CardAbility;

public class CrystalizeAction extends ElementmorphoseAction {

    public CrystalizeAction() {
        super(
                CardAbility.ICE,
                DynamicDungeon::makeCardIce
        );
    }
}
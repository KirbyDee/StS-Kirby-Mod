package theSorcerer.actions;

import theSorcerer.DynamicDungeon;
import theSorcerer.patches.cards.CardAbility;

public class ColdShowerAction extends ElementmorphoseAction {

    public ColdShowerAction() {
        super(
                CardAbility.ICE,
                DynamicDungeon::makeCardIce
        );
    }
}
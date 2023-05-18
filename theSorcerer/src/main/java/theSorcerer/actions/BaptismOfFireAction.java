package theSorcerer.actions;

import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;

public class BaptismOfFireAction extends ElementmorphoseAction {

    public BaptismOfFireAction() {
        super(
                CardModifier.FIRE,
                DynamicDungeon::makeCardFire
        );
    }
}

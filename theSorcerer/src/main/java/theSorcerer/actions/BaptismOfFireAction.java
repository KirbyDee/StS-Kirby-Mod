package theSorcerer.actions;

import theSorcerer.DynamicDungeon;
import theSorcerer.patches.cards.CardAbility;

public class BaptismOfFireAction extends ElementmorphoseAction {

    public BaptismOfFireAction() {
        super(
                CardAbility.FIRE,
                DynamicDungeon::makeCardFire
        );
    }
}

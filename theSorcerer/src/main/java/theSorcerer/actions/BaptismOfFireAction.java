package theSorcerer.actions;

import theSorcerer.cards.SorcererCardTags;
import theSorcerer.patches.cards.CardAbility;

public class BaptismOfFireAction extends ElementmorphoseAction {

    public BaptismOfFireAction() {
        super(
                SorcererCardTags.FIRE,
                CardAbility.FIRE,
                SorcererCardTags.ICE
        );
    }
}

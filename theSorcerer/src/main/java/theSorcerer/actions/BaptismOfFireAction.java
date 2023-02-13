package theSorcerer.actions;

import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCardTags;

public class BaptismOfFireAction extends ElementmorphoseAction {

    public BaptismOfFireAction(
            boolean upgraded
    ) {
        super(
                SorcererCardTags.FIRE,
                DynamicCard.CardAbility.FIRE,
                SorcererCardTags.ICE,
                DynamicCard.CardAbility.ICE,
                upgraded
        );
    }
}

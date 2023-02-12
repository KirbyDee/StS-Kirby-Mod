package theSorcerer.actions;

import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCardTags;

public class BaptismOfFireAction extends ElementmorphoseAction {

    public BaptismOfFireAction(
            boolean upgraded
    ) {
        super(
                SorcererCardTags.FIRE,
                DynamicCard.FIRE_CARD_PREFIX,
                SorcererCardTags.ICE,
                DynamicCard.ICE_CARD_PREFIX,
                upgraded
        );
    }
}

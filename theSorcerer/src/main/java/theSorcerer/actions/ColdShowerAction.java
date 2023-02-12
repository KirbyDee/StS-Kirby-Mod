package theSorcerer.actions;

import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCardTags;

public class ColdShowerAction extends ElementmorphoseAction {

    public ColdShowerAction(
            boolean upgraded
    ) {
        super(
                SorcererCardTags.ICE,
                DynamicCard.ICE_CARD_PREFIX,
                SorcererCardTags.FIRE,
                DynamicCard.FIRE_CARD_PREFIX,
                upgraded
        );
    }
}
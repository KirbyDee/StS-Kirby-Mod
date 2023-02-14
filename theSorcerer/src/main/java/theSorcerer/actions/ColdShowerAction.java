package theSorcerer.actions;

import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCardTags;

public class ColdShowerAction extends ElementmorphoseAction {

    public ColdShowerAction() {
        super(
                SorcererCardTags.ICE,
                DynamicCard.CardAbility.ICE,
                SorcererCardTags.FIRE,
                DynamicCard.CardAbility.FIRE
        );
    }
}
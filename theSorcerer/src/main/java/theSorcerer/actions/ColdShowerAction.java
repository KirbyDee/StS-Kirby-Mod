package theSorcerer.actions;

import theSorcerer.cards.SorcererCardTags;
import theSorcerer.patches.cards.CardAbility;

public class ColdShowerAction extends ElementmorphoseAction {

    public ColdShowerAction() {
        super(
                SorcererCardTags.ICE,
                CardAbility.ICE,
                SorcererCardTags.FIRE,
                CardAbility.FIRE
        );
    }
}
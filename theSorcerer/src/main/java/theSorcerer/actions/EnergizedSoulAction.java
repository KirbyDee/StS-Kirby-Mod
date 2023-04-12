package theSorcerer.actions;

import theSorcerer.DynamicDungeon;
import theSorcerer.relics.EnergizedSoul;

public class EnergizedSoulAction extends ElementSoulAction {

    public EnergizedSoulAction(
            EnergizedSoul relic
    ) {
        super(relic);
    }

    @Override
    protected void applySoulPower() {
        DynamicDungeon.applyPresenceOfMind();
    }
}

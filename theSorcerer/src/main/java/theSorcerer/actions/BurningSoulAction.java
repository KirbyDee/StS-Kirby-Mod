package theSorcerer.actions;

import theSorcerer.DynamicDungeon;
import theSorcerer.relics.BurningSoul;

public class BurningSoulAction extends ElementSoulAction {

    public BurningSoulAction(
            BurningSoul relic,
            int amount
    ) {
        super(relic, amount);
    }

    @Override
    protected void applySoulPower() {
        DynamicDungeon.applyHeated(this.amount);
    }
}

package theSorcerer.actions;

import theSorcerer.DynamicDungeon;
import theSorcerer.relics.FreezingSoul;

public class FreezingSoulAction extends ElementSoulAction {

    public FreezingSoulAction(
            FreezingSoul relic,
            int amount
    ) {
        super(relic, amount);
    }

    @Override
    protected void applySoulPower() {
        DynamicDungeon.applyChilled(this.amount);
    }
}

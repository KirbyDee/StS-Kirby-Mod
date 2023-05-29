package theSorcerer.relics;

import theSorcerer.actions.ElementSoulAction;
import theSorcerer.actions.EnergizedSoulAction;
import theSorcerer.powers.buff.PresenceOfMindPower;

import java.util.function.Function;

public class EnergizedSoul extends ElementSoul {

    public EnergizedSoul() {
        super(
                EnergizedSoul.class
        );
    }

    @Override
    public void onEquip() {
        this.counter = 0;
    }

    @Override
    public void atTurnStart() {
        if (this.counter < 0) {
            this.counter = 1;
        }
        else {
            ++this.counter;
        }

        if (this.counter == ELEMENT_AMOUNT) {
            this.counter = 0;
            super.atTurnStart();
        }
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        addTip(PresenceOfMindPower.class);
    }

    @Override
    protected Function<Integer, ElementSoulAction> toElementSoulAction() {
        return i -> new EnergizedSoulAction(this);
    }

}

package theSorcerer.relics;

import theSorcerer.actions.ElementSoulAction;
import theSorcerer.actions.EnergizedSoulAction;
import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.buff.PresenceOfMindPower;

import java.util.function.Function;

public class EnergizedSoul extends ElementSoul {

    public EnergizedSoul() {
        super(
                EnergizedSoul.class
        );
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

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

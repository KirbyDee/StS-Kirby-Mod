package theSorcerer.relics;

import theSorcerer.actions.ElementSoulAction;
import theSorcerer.actions.FreezingSoulAction;

import java.util.function.Function;

public class FreezingSoul extends ElementSoul {

    public FreezingSoul() {
        super(
                FreezingSoul.class
        );
    }

    @Override
    protected Function<Integer, ElementSoulAction> toElementSoulAction() {
        return i -> new FreezingSoulAction(this, i);
    }

    @Override
    protected Class<? extends ElementSoul> getOppositeSoul() {
        return BurningSoul.class;
    }
}

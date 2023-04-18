package theSorcerer.relics;

import theSorcerer.actions.BurningSoulAction;
import theSorcerer.actions.ElementSoulAction;

import java.util.function.Function;

public class BurningSoul extends ElementSoul {

    public BurningSoul() {
        super(
                BurningSoul.class
        );
    }

    @Override
    protected Function<Integer, ElementSoulAction> toElementSoulAction() {
        return i -> new BurningSoulAction(this, i);
    }

    @Override
    protected Class<? extends ElementSoul> getOppositeSoul() {
        return FreezingSoul.class;
    }
}

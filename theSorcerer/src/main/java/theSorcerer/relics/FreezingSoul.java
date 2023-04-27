package theSorcerer.relics;

import theSorcerer.actions.ElementSoulAction;
import theSorcerer.actions.FreezingSoulAction;
import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.debuff.FrozenPower;

import java.util.function.Function;

public class FreezingSoul extends ElementSoul {

    public FreezingSoul() {
        super(
                FreezingSoul.class
        );
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        addTip(
                ChilledPower.class,
                FrozenPower.class
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

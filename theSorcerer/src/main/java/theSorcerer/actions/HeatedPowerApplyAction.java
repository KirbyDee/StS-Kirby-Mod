package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.buff.ElementPower;
import theSorcerer.powers.buff.HeatedPower;

public class HeatedPowerApplyAction extends ElementPowerApplyAction {

    public HeatedPowerApplyAction(
            AbstractCreature owner,
            int amount
    ) {
        super(owner, amount);
    }

    @Override
    protected boolean hasOppositeElementPower() {
        return DynamicDungeon.isChilled();
    }

    @Override
    protected ElementPower<?> getElementPower(
            final AbstractCreature owner,
            final int amount
    ) {
        return new HeatedPower(owner, amount);
    }

    @Override
    protected void showVFX(final AbstractCreature owner) {
        addToTop(
                new VFXAction(
                        owner,
                        new InflameEffect(owner),
                        0.25F
                )
        );
    }
}

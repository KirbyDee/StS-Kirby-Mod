package theSorcerer.actions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.buff.ElementPower;

public class ChilledPowerApplyAction extends ElementPowerApplyAction {

    public ChilledPowerApplyAction(
            AbstractCreature owner,
            int amount
    ) {
        super(owner, amount);
    }

    @Override
    protected boolean hasOppositeElementPower() {
        return DynamicDungeon.isHeated();
    }

    @Override
    protected ElementPower<?> getElementPower(
            final AbstractCreature owner,
            final int amount
    ) {
        return new ChilledPower(owner, amount);
    }

    @Override
    protected void showVFX(AbstractCreature owner) {
        // TODOO
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1F);
    }
}
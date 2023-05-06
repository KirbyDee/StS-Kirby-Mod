package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theSorcerer.powers.DynamicAmountPower;
import theSorcerer.powers.DynamicPower;

public abstract class StartOfTurnPower extends DynamicAmountPower {

    private final String powerToCheck;

    public StartOfTurnPower(
            Class<? extends StartOfTurnPower> thisClazz,
            AbstractCreature owner,
            Class<? extends DynamicPower> powerToCheck,
            int amount
    ) {
        super(
                thisClazz,
                owner,
                amount
        );
        this.powerToCheck = DynamicPower.getID(powerToCheck);
        this.isTurnBased = true;
        this.canGoNegative = false;

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        AbstractPlayer player = (AbstractPlayer) this.owner;
        if (player.hasPower(this.powerToCheck)) {
            flash();
            applyPower();
        }
    }

    protected abstract void applyPower();

    @Override
    public void updateDescription() {
        this.description = this.descriptions[0] + this.amount + this.descriptions[1];
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.BUFF;
    }
}


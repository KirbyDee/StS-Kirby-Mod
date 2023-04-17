package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theSorcerer.powers.DynamicAmountPower;

public abstract class EndOfTurnElementPower extends DynamicAmountPower {

    private final String elementPower;

    public EndOfTurnElementPower(
            final AbstractCreature owner,
            final String powerId,
            final String elementPower,
            final int amount
    ) {
        super(owner, powerId, amount);
        this.elementPower = elementPower;

        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.canGoNegative = false;

        updateDescription();
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer) {
            AbstractPlayer player = (AbstractPlayer) this.owner;
            if (player.hasPower(this.elementPower)) {
                flash();
                applyPower();
            }
        }
    }

    protected abstract void applyPower();

    @Override
    public void updateDescription() {
        description = this.descriptions[0] + this.amount + this.descriptions[1];
    }
}


package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.buff.ElementPower;

public abstract class ElementPowerApplyAction extends AbstractGameAction {

    private final AbstractCreature owner;

    public ElementPowerApplyAction(
            AbstractCreature owner,
            int amount
    ) {
        this.owner = owner;
        this.amount = amount;
        this.actionType = ActionType.POWER;
    }

    @Override
    public void update() {
        DynamicDungeon.runIfNotElementless(this::tryApplyingElementPower);
        this.isDone = true;
    }

    protected abstract boolean hasOppositeElementPower();

    protected abstract ElementPower<?> getElementPower(final AbstractCreature owner, final int amount);

    protected abstract void showVFX(final AbstractCreature owner);

    private void tryApplyingElementPower() {
        if (doesValidElementSwitch()) {
            showVFX(this.owner);
            addToTop(
                    new ApplyPowerAction(
                            this.owner,
                            this.owner,
                            getElementPower(this.owner, this.amount),
                            this.amount
                    )
            );
        }
    }

    private boolean doesValidElementSwitch() {
        if (hasOppositeElementPower()) {
            DynamicDungeon.loseElements();
            return !DynamicDungeon.applyElementless();
        }
        return true;
    }
}

package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theSorcerer.DynamicDungeon;
import theSorcerer.util.ElementAmount;

public class ElementalPathAction extends AbstractGameAction {

    @Override
    public void update() {
        final ElementAmount elementAmount = DynamicDungeon.getElementAmount();

        if (elementAmount.hasAmount()) {
            switchElements(elementAmount);
        }

        this.isDone = true;
        this.actionType = ActionType.SPECIAL;
    }

    private void switchElements(final ElementAmount elementAmount) {
        DynamicDungeon.loseAllElements();
        if (elementAmount.isHeated()) {
            DynamicDungeon.applyChilled(elementAmount.getHeated());
        }
        else if (elementAmount.isChilled()) {
            DynamicDungeon.applyHeated(elementAmount.getChilled());
        }
    }
}

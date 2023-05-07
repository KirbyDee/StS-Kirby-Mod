package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theSorcerer.DynamicDungeon;

public class RethinkingAction extends AbstractGameAction {

    @Override
    public void update() {
        int elementAmount = DynamicDungeon.getElementAmount();
        if (elementAmount > 0) {
            switchElements(elementAmount);
        }

        this.isDone = true;
        this.actionType = ActionType.SPECIAL;
    }

    private void switchElements(final int elementAmount) {
        DynamicDungeon.loseAllElements();
        if (DynamicDungeon.isHeated()) {
            DynamicDungeon.applyChilled(elementAmount);
        }
        else if (DynamicDungeon.isChilled()) {
            DynamicDungeon.applyHeated(elementAmount);
        }
    }
}

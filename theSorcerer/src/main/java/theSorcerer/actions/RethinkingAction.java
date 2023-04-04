package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.buff.HeatedPower;

public class RethinkingAction extends AbstractGameAction {

    private final AbstractPlayer owner;

    public RethinkingAction(
            AbstractPlayer owner
    ) {
        this.owner = owner;
    }

    @Override
    public void update() {
        int elementAmount = DynamicDungeon.getElementAmount();
        if (elementAmount > 0) {
            switchElements(elementAmount);
        }

        this.isDone = true;
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

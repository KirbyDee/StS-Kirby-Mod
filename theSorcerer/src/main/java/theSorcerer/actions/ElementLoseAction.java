package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import theSorcerer.powers.DynamicPower;
import theSorcerer.powers.SelfRemovablePower;
import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.buff.HeatedPower;

public class ElementLoseAction extends AbstractGameAction {

    private final AbstractPlayer owner;

    public ElementLoseAction(
            AbstractPlayer owner
    ) {
        this.owner = owner;
    }

    @Override
    public void update() {
        removeElementPower(DynamicPower.getID(HeatedPower.class));
        removeElementPower(DynamicPower.getID(ChilledPower.class));
        this.isDone = true;
    }

    private void removeElementPower(final String powerID) {
        if (this.owner.hasPower(powerID)) {
            ((SelfRemovablePower) this.owner.getPower(powerID)).removeSelf();
        }
    }
}

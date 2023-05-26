package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.buff.PresenceOfMindPower;

public class PresenceOfMindApplyAction extends AbstractGameAction {

    private final AbstractCreature owner;

    public PresenceOfMindApplyAction(
            AbstractCreature owner
    ) {
        this.owner = owner;
        this.actionType = ActionType.POWER;
    }

    @Override
    public void update() {
        DynamicDungeon.runIfNotElementless(this::applyPresenceOfMind);
        this.isDone = true;
    }

    private void applyPresenceOfMind() {
        if (!DynamicDungeon.hasPresenceOfMind()) {
            addToBot(
                    new ApplyPowerAction(
                            this.owner,
                            this.owner,
                            new PresenceOfMindPower(AbstractDungeon.player)
                    )
            );
        }
    }
}

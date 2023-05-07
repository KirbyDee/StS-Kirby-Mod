package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MisdirectionAction extends AbstractGameAction {
    private final AbstractMonster targetMonster;

    public MisdirectionAction(
            AbstractMonster targetMonster
    ) {
        this.targetMonster = targetMonster;
        this.actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        // TODOO: sound / VFX?
        this.targetMonster.rollMove();
        this.targetMonster.createIntent();

        this.isDone = true;
    }
}

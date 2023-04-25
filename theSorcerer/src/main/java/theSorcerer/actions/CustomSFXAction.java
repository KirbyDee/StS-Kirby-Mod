package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import theSorcerer.core.CustomCardCrawlGame;

public class CustomSFXAction extends AbstractGameAction {

    private final String key;
    private final float pitchVar;
    private final boolean adjust;

    public CustomSFXAction(String key) {
        this(key, 0.0F, false);
    }

    public CustomSFXAction(String key, float pitchVar) {
        this(key, pitchVar, false);
    }

    public CustomSFXAction(String key, float pitchVar, boolean pitchAdjust) {
        this.key = key;
        this.pitchVar = pitchVar;
        this.adjust = pitchAdjust;
        this.actionType = ActionType.WAIT;
    }

    public void update() {
        if (!this.adjust) {
            CustomCardCrawlGame.sound.play(this.key, this.pitchVar);
        }
        else {
            CustomCardCrawlGame.sound.playA(this.key, this.pitchVar);
        }

        this.isDone = true;
    }
}

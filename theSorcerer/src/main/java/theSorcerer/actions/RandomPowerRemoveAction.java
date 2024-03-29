package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.DynamicDungeon;

import java.util.Collections;
import java.util.List;

public class RandomPowerRemoveAction extends AbstractGameAction {

    private final AbstractCreature creature;

    private final AbstractPower.PowerType powerType;

    public RandomPowerRemoveAction(
            AbstractCreature creature,
            AbstractPower.PowerType powerType,
            int amount
    ) {
        this.creature = creature;
        this.powerType = powerType;
        this.amount = amount;
        this.actionType = ActionType.REDUCE_POWER;
    }

    @Override
    public void update() {
        for (int i = 0; i < this.amount; i++) {
            DynamicDungeon.runIfNotArtifact(this.creature, this::removeRandomPower);
        }
        this.isDone = true;
    }

    private void removeRandomPower() {
        List<AbstractPower> copyPowers = this.creature.powers;
        Collections.shuffle(copyPowers, AbstractDungeon.miscRng.random);
        copyPowers
                .stream()
                .filter(p -> p.type == powerType)
                .findAny()
                .ifPresent(this::removePower);
    }

    private void removePower(final AbstractPower power) {
        power.flash();
        CardCrawlGame.sound.play("ORB_DARK_EVOKE", 0.1f);
        addToBot(new WaitAction(0.25F));
        if (this.amount <= 0) {
            addToBot(
                    new RemoveSpecificPowerAction(
                            this.creature,
                            this.creature,
                            power.ID
                    )
            );
        } else {
            addToBot(
                    new ReducePowerAction(
                            this.creature,
                            this.creature,
                            power.ID,
                            1
                    )
            );
        }
    }
}

package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
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
    }

    @Override
    public void update() {
        for (int i = 0; i < this.amount; i++) {
            removePower();
        }
        this.isDone = true;
    }

    private void removePower() {
        if (this.creature.hasPower(ArtifactPower.POWER_ID)) {
            this.creature.getPower(ArtifactPower.POWER_ID).onSpecificTrigger();
            return;
        }

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
        CardCrawlGame.sound.play("JAW_WORM_DEATH"); // TODO: different sound
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

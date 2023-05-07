package theSorcerer.powers.debuff;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theSorcerer.actions.IgniteLoseHpAction;
import theSorcerer.powers.DynamicReducePerTurnPower;

public class IceAgePower extends DynamicReducePerTurnPower {

    private final AbstractCreature source;

    public IceAgePower(
            AbstractCreature owner,
            AbstractCreature source,
            int turns,
            boolean isSourceMonster

    ) {
        super(
                IceAgePower.class,
                owner,
                turns,
                isSourceMonster
        );
        this.source = source;

        updateDescription();
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.05F);
    }

    @Override
    public void onGainedBlock(float blockAmount) {
        flashWithoutSound();
        this.owner.loseBlock();
    }

    @Override
    public void updateDescription() {
        description = this.descriptions[0] + this.amount + (this.amount > 1 ? this.descriptions[1] : this.descriptions[2]);
    }

    @Override
    public AbstractPower makeCopy() {
        return new IceAgePower(this.owner, this.source, this.amount, this.isSourceMonster);
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.DEBUFF;
    }
}

package theSorcerer.powers.debuff;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theSorcerer.actions.IgniteLoseHpAction;
import theSorcerer.powers.DynamicReducePerTurnPower;

public class IgnitePower extends DynamicReducePerTurnPower {

    private final AbstractCreature source;

    private final int loseLifeAmount;

    public IgnitePower(
            AbstractCreature owner,
            AbstractCreature source,
            int loseLifeAmount,
            int turns,
            boolean isSourceMonster

    ) {
        super(
                IgnitePower.class,
                owner,
                turns,
                isSourceMonster
        );
        this.source = source;
        this.loseLifeAmount = loseLifeAmount;

        updateDescription();
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.05F);
    }

    @Override
    public void atEndOfRound() {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            addToBot(
                    new IgniteLoseHpAction(
                            this.owner,
                            this.source,
                            this.loseLifeAmount,
                            AbstractGameAction.AttackEffect.FIRE
                    )
            );
        }
        super.atEndOfRound();
    }

    @Override
    public void updateDescription() {
        description = this.descriptions[0] + this.loseLifeAmount + this.descriptions[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new IgnitePower(this.owner, this.source, this.loseLifeAmount, this.amount, this.isSourceMonster);
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.DEBUFF;
    }
}

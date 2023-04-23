package theSorcerer.powers.debuff;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.SmokePuffEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.actions.ReplaceMonsterAction;
import theSorcerer.powers.DynamicReducePerTurnPower;

public class PolymorphPower extends DynamicReducePerTurnPower {

    private static final Logger LOG = LogManager.getLogger(PolymorphPower.class.getName());

    private final AbstractMonster sheep;
    private final AbstractMonster monster;

    public PolymorphPower(
            AbstractMonster sheep,
            AbstractMonster monster,
            int amount
    ) {
        super(
                PolymorphPower.class,
                sheep,
                amount,
                true
        );
        this.sheep = sheep;
        this.monster = monster;

        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        this.sheep.init();
        this.sheep.applyPowers();
        this.sheep.showHealthBar();
    }

    @Override
    public void onSpecificTrigger() {
        LOG.info("Sheep took damage or died");
        sheep.powers.remove(this);
        unSheep();
    }

    @Override
    public void onRemove() {
        LOG.info("Polymorph has been removed");
        unSheep();
    }

    public void unSheep() {
        LOG.info("unSheep " + this.monster.name);
        replaceSheepWithOriginalMonster();
    }

    // TODO: sheep sound
    private void replaceSheepWithOriginalMonster() {
        addToBot(
                new VFXAction(
                        new SmokePuffEffect(
                                this.sheep.hb.cX,
                                this.sheep.hb.cY
                        )
                )
        );
        addToBot(new WaitAction(0.1F));
        addToBot(
                new ReplaceMonsterAction(
                        this.sheep,
                        this.monster,
                        false
                )
        );
    }

    @Override
    public void updateDescription() {
        String turnsString = this.amount > 1 ? this.descriptions[3] : this.descriptions[2];
        description = this.descriptions[0] + this.monster.name + this.descriptions[1] + this.amount + turnsString + this.descriptions[4];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PolymorphPower(this.sheep, this.monster, this.amount);
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.DEBUFF;
    }
}

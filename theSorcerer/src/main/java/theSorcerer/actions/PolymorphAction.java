package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SmokePuffEffect;
import theSorcerer.DynamicDungeon;
import theSorcerer.monsters.special.Sheep;
import theSorcerer.powers.debuff.PolymorphPower;

public class PolymorphAction extends AbstractGameAction {

    private final AbstractMonster monster;

    public PolymorphAction(
            AbstractMonster monster,
            AbstractPlayer source,
            int amount
    ) {
        this.monster = monster;
        this.source = source;
        this.amount = amount;
    }

    @Override
    public void update() {
        DynamicDungeon.runIfNotArtifact(this.monster, this::applyPolymorphPower);
        this.isDone = true;
    }

    private void applyPolymorphPower() {
        AbstractMonster sheep = this.monster instanceof Sheep ?
                this.monster :
                polymorphMonster();

        addToBot(
                new ApplyPowerAction(
                        sheep,
                        this.source,
                        new PolymorphPower(
                                sheep,
                                this.monster,
                                this.amount
                        ),
                        this.amount
                )
        );
    }

    private Sheep polymorphMonster() {
        Sheep sheep = new Sheep(this.monster);
        sheep.init();
        sheep.applyPowers();
        sheep.showHealthBar();

        addToBot(
                new VFXAction(
                        new SmokePuffEffect(
                                this.monster.hb.cX,
                                this.monster.hb.cY
                        )
                )
        );
        addToBot(new SFXAction("ATTACK_MAGIC_FAST_3"));
        addToBot(new WaitAction(0.1F));
        addToBot(
                new ReplaceMonsterAction(
                        this.monster,
                        sheep,
                        true
                )
        );
        addToBot(new WaitAction(0.25F));
        addToBot(new CustomSFXAction("SHEEP"));

        return sheep;
    }
}

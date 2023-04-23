package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SmokePuffEffect;
import theSorcerer.DynamicDungeon;
import theSorcerer.monsters.special.Sheep;
import theSorcerer.powers.debuff.PolymorphPower;

public class ReplaceMonsterAction extends AbstractGameAction {

    private final AbstractMonster fromMonster;

    private  final AbstractMonster toMonster;

    private final boolean newMonster;

    public ReplaceMonsterAction(
            AbstractMonster fromMonster,
            AbstractMonster toMonster,
            boolean newMonster
    ) {
        this.fromMonster = fromMonster;
        this.toMonster = toMonster;
        this.newMonster = newMonster;
    }

    @Override
    public void update() {addToBot(new WaitAction(0.25F));
        int index = AbstractDungeon.getCurrRoom().monsters.monsters.indexOf(this.fromMonster);
        AbstractDungeon.getCurrRoom().monsters.monsters.remove(index);
        AbstractDungeon.getCurrRoom().monsters.addMonster(index, this.toMonster);
        if (newMonster) {
            this.toMonster.powers.addAll(this.fromMonster.powers);
            this.toMonster.setMove((byte) 0, AbstractMonster.Intent.UNKNOWN);
            this.toMonster.createIntent();
        }
        else {
            this.toMonster.powers = this.fromMonster.powers;
        }

        this.isDone = true;
    }
}

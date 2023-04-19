package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.DynamicAmountPower;

public class ElementalStormPower extends DynamicAmountPower {

    public ElementalStormPower(
            AbstractCreature owner,
            int amount
    ) {
        super(
                ElementalStormPower.class,
                owner,
                amount
        );

        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (DynamicDungeon.isElementCard(card)) {
            flash();
            AbstractDungeon.getCurrRoom().monsters.monsters.forEach(this::applyWeakAndVulnerable);
        }
    }

    private void applyWeakAndVulnerable(AbstractMonster monster) {
        // weak
        addToBot(
                new ApplyPowerAction(
                        monster,
                        this.owner,
                        new WeakPower(
                                monster,
                                this.amount,
                                false
                        ),
                        this.amount,
                        true,
                        AbstractGameAction.AttackEffect.NONE
                )
        );

        // vulnerable
        addToBot(
                new ApplyPowerAction(
                        monster,
                        this.owner,
                        new VulnerablePower(
                                monster,
                                this.amount,
                                false
                        ),
                        this.amount,
                        true,
                        AbstractGameAction.AttackEffect.NONE
                )
        );
    }

    @Override
    public void atEndOfRound() {
        removeSelf();
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0] + this.amount + descriptions[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ElementalStormPower(this.owner, this.amount);
    }
}

package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.DynamicAmountPower;

public class ElementalStormPower extends DynamicAmountPower {

    public ElementalStormPower(
            AbstractPlayer owner,
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

            addToBot(
                    new DamageAllEnemiesAction(
                            (AbstractPlayer) this.owner,
                            this.amount,
                            DamageInfo.DamageType.THORNS,
                            AbstractGameAction.AttackEffect.NONE
                    )
            );
            addToBot(
                    new GainBlockAction(
                            this.owner,
                            this.owner,
                            this.amount
                    )
            );
        }
    }

    @Override
    public void atEndOfRound() {
        removeSelf();
    }

    @Override
    public void updateDescription() {
        this.description = this.descriptions[0] + this.amount + this.descriptions[1] + this.amount + this.descriptions[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ElementalStormPower((AbstractPlayer) this.owner, this.amount);
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.BUFF;
    }
}

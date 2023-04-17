package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.DynamicDungeon;
import theSorcerer.patches.cards.CardAbility;
import theSorcerer.powers.DynamicAmountPower;

public abstract class ElementPower<E extends AbstractPower> extends DynamicAmountPower {

    private static final Logger LOG = LogManager.getLogger(ElementPower.class.getName());

    public ElementPower(
            final AbstractCreature owner,
            final int amount,
            final String thisEvolvedPowerID
    ) {
        super(owner, thisEvolvedPowerID, amount);
        this.isTurnBased = true;
        this.canGoNegative = false;

        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        applyExtraPower(stackAmount);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        applyExtraPower(this.amount);
    }

    private void applyExtraPower(final int amount) {
        if (amount > 0) {
            LOG.info("Apply Extra Power: " + amount);
            addToTop(
                    new ApplyPowerAction(
                            this.owner,
                            this.owner,
                            createExtraPower(amount)
                    )
            );
        }
    }

    protected abstract E createExtraPower(final int amount);

    @Override
    public void atEndOfRound() {
        reducePowerToZero();
    }

    @Override
    public void removeSelf() {
        reducePowerToZero();
        super.removeSelf();
    }

    public void reducePowerToZero() {
        reducePower(this.ID);
        reduceExtraPower();
    }

    private void reduceExtraPower() {
        reducePower(getExtraPowerId());
    }

    private void reducePower(final String powerId) {
        if (this.amount <= 0) {
            return;
        }

        LOG.info("Reduce " + powerId + " by  " + this.amount);
        addToBot(
                new ReducePowerAction(
                        this.owner,
                        this.owner,
                        powerId,
                        this.amount
                )
        );
    }

    protected abstract String getExtraPowerId();

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        CardAbility ability = getElementLoseAbility();
        if (DynamicDungeon.cardHasAbility(card, ability)) {
            LOG.info(ability + " applied, but " + this.ID + " already existing -> remove " + this.ID);
            removeSelf();
            DynamicDungeon.applyElementless();
        }
    }

    protected abstract CardAbility getElementLoseAbility();

    @Override
    public void updateDescription() {
        description = this.descriptions[0] + this.amount + this.descriptions[1] + this.amount + this.descriptions[2];
    }
}

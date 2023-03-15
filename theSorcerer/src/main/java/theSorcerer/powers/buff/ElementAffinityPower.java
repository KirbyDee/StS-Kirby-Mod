package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.SelfRemovablePower;
import theSorcerer.powers.debuff.ElementlessPower;
import theSorcerer.relics.ElementalMaster;

public abstract class ElementAffinityPower<E extends ElementEvolvePower<?>> extends SelfRemovablePower {

    private static final Logger LOG = LogManager.getLogger(ElementAffinityPower.class.getName());

    public ElementAffinityPower(
            final AbstractCreature owner,
            final int amount,
            final String id
    ) {
        super(owner, id);
        this.isTurnBased = true;
        this.canGoNegative = false;

        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }

        updateDescription();
        checkForEvolve();
    }

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
        if (this.amount <= 0) {
            return;
        }

        LOG.info("Reduce " + this.ID + " to zero");
        addToBot(
                new ReducePowerAction(
                        this.owner,
                        this.owner,
                        this.ID,
                        this.amount
                )
        );
    }

    @Override
    public void updateDescription() {
        description = this.descriptions[0] + "3" + this.descriptions[1]; // TODO
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        AbstractCard.CardTags tag = getAffinityLoseTag();
        if (card.tags.contains(tag)) {
            LOG.info(tag + " applied, but " + this.ID + " already existing");
            removeSelf();
            DynamicDungeon.applyElementless();
        }
    }

    protected abstract AbstractCard.CardTags getAffinityLoseTag();

    @Override
    public void stackPower(int stackAmount) {
        stackAmount(stackAmount);
        checkForEvolve();
    }

    private void stackAmount(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            removeSelf();
        }

        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    private void checkForEvolve() {
        LOG.info(this.ID + " amount: " + this.amount);
        if (!this.owner.hasPower(getEvolvedPowerId()) && this.amount >= 3) { // TODO
            applyElementEvolve();
        }
    }

    private void applyElementEvolve() {
        LOG.info("Apply evolve power");
        addToBot(
                new ApplyPowerAction(
                        this.owner,
                        this.owner,
                        createEvolvePower()
                )
        );
    }

    protected abstract E createEvolvePower();

    protected abstract String getEvolvedPowerId();
}

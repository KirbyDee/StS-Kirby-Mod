package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.patches.cards.CardAbility;
import theSorcerer.powers.debuff.FrozenPower;

public class ChilledPower extends ElementPower<DexterityPower> {

    private static final Logger LOG = LogManager.getLogger(ChilledPower.class.getName());

    public ChilledPower(
            final AbstractCreature owner,
            final int amount
    ) {
        super(
                ChilledPower.class,
                owner,
                amount
        );
    }

    protected DexterityPower createExtraPower(final int amount) {
        return new DexterityPower(this.owner, amount);
    }

    @Override
    protected String getExtraPowerId() {
        return DexterityPower.POWER_ID;
    }

    @Override
    protected CardAbility getElementLoseAbility() {
        return CardAbility.FIRE;
    }

    @Override
    public AbstractPower makeCopy() {
        return new ChilledPower(this.owner, this.amount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        // have to fully block an attack (currentBlock is the block after the attack happened)
        if (this.owner.currentBlock >= 0 && this.amount > 0) {
            LOG.info("Fully blocked attack, apply Frozen amount " + this.amount);
            addToTop(
                    new ApplyPowerAction(
                            info.owner,
                            this.owner,
                            new FrozenPower(info.owner, this.amount, true)
                    )
            );
        }

        return damageAmount;
    }
}


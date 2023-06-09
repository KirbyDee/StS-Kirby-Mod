package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.powers.debuff.AblazePower;

public class HeatedPower extends ElementPower<StrengthPower> {

    private static final Logger LOG = LogManager.getLogger(HeatedPower.class.getName());

    public HeatedPower(
            final AbstractCreature owner,
            final int amount
    ) {
        super(
                HeatedPower.class,
                owner,
                amount
        );
    }

    protected StrengthPower createExtraPower(final int amount) {
        return new StrengthPower(this.owner, amount);
    }

    @Override
    protected String getExtraPowerId() {
        return StrengthPower.POWER_ID;
    }

    @Override
    public AbstractPower makeCopy() {
        return new HeatedPower(this.owner, this.amount);
    }

    @Override
    public void onAttack(
            DamageInfo info,
            int damageAmount,
            AbstractCreature target
    ) {
        if (target != info.owner &&
                info.owner == this.owner &&
                info.type == DamageInfo.DamageType.NORMAL &&
                this.amount > 0 &&
                damageAmount > 0
        ) {
            LOG.info("Inflicting damage to target with attack, apply Ablaze amount " + this.amount);
            addToTop(
                    new ApplyPowerAction(
                            target,
                            this.owner,
                            new AblazePower(target, this.amount, true)
                    )
            );
        }
    }
}

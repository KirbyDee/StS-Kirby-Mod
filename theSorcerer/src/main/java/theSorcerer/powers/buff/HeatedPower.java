package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.KirbyDeeMod;
import theSorcerer.powers.debuff.AblazePower;

public class HeatedPower extends ElementEvolvePower<StrengthPower> {

    private static final Logger LOG = LogManager.getLogger(HeatedPower.class.getName());

    private static final String POWER_NAME = "HeatedPower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public HeatedPower(
            final AbstractCreature owner,
            final int affinityAmount
    ) {
        super(owner, affinityAmount, POWER_ID, FireAffinityPower.POWER_ID, IceAffinityPower.POWER_ID);
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
        return new HeatedPower(this.owner, this.affinityAmount);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (target != info.owner &&
                info.owner == this.owner &&
                info.type == DamageInfo.DamageType.NORMAL &&
                damageAmount > 0 &&
                target.currentBlock < damageAmount) {
            LOG.info("Inflicting damage to target with attack, apply Ablaze amount " + this.affinityAmount);
            addToBot(
                    new ApplyPowerAction(
                            target,
                            this.owner,
                            new AblazePower(target, this.affinityAmount, true)
                    )
            );
        }
    }
}

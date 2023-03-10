package theSorcerer.powers.debuff;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.KirbyDeeMod;

public class FrozenPower extends ElementDebuffPower {

    private static final Logger LOG = LogManager.getLogger(FrozenPower.class.getName());

    public static final String POWER_NAME = "FrozenPower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public FrozenPower(
            final AbstractCreature owner,
            final int amount,
            boolean isSourceMonster
    ) {
        super(owner, amount, POWER_ID, isSourceMonster);
    }

    @Override
    public AbstractPower makeCopy() {
        return new FrozenPower(this.owner, this.amount, this.isSourceMonster);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null &&
                info.type == DamageInfo.DamageType.NORMAL &&
                info.owner != this.owner) {
            LOG.info("Target being attacked! Deal " + this.amount + " to it");
            addToTop(
                    new DamageAction(
                            this.owner,
                            new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS),
                            AbstractGameAction.AttackEffect.FIRE // TODO
                    )
            );
        }

        return damageAmount;
    }
}

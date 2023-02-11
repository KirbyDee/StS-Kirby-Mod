package theSorcerer.powers.debuff;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.KirbyDeeMod;

public class AblazePower extends ElementDebuffPower {

    private static final Logger LOG = LogManager.getLogger(AblazePower.class.getName());

    public static final String POWER_NAME = "AblazePower"; // TODO: get class name and put into parent

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public AblazePower(
            final AbstractCreature owner,
            final int amount,
            boolean isSourceMonster
    ) {
        super(owner, amount, POWER_ID, isSourceMonster);
    }

    @Override
    public AbstractPower makeCopy() {
        return new AblazePower(this.owner, this.amount, this.isSourceMonster);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.owner != null &&
                info.type == DamageInfo.DamageType.NORMAL &&
                info.owner == this.owner) {
            LOG.info("Target attacking! Deal " + this.amount + " to itself");
            addToTop(
                    new DamageAction(
                            this.owner,
                            new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS),
                            AbstractGameAction.AttackEffect.FIRE
                    )
            );
        }
    }
}

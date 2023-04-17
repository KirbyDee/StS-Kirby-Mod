package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.KirbyDeeMod;
import theSorcerer.actions.DamageMultipleEnemiesAction;
import theSorcerer.powers.DynamicAmountPower;

import java.util.List;

public class CollateralDamagePower extends DynamicAmountPower {

    private static final Logger LOG = LogManager.getLogger(CollateralDamagePower.class.getName());

    public static final String POWER_NAME = "CollateralDamagePower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public CollateralDamagePower(
            AbstractCreature owner,
            int amount
    ) {
        super(owner, POWER_ID, amount);

        updateDescription();
    }

    @Override
    public void onAttack(
            DamageInfo info,
            int damageAmount,
            AbstractCreature target
    ) {
        if (target != info.owner &&
                target instanceof AbstractMonster &&
                info.owner == this.owner &&
                info.type == DamageInfo.DamageType.NORMAL &&
                this.amount > 0 &&
                damageAmount > 0) {
            collateralDamage((AbstractMonster) target, computeCollateralDamage(damageAmount));
        }
    }

    private int computeCollateralDamage(final int damage) {
        return Math.round(damage * this.amount / 100f);
    }

    private void collateralDamage(
            final AbstractMonster target,
            final int damage
    ) {
        LOG.info("Inflicting damage to target with attack, deal damage to all enemies except targeted one: " + damage);
        List<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
        int[] multiDamage = new int[monsters.size()];
        for (int i = 0; i < monsters.size(); i++) {
            AbstractMonster monster = monsters.get(i);
            if (monster.equals(target)) {
                multiDamage[i] = -1;
                continue;
            }
            multiDamage[i] = damage;
        }


        flash();
        addToBot(
                new DamageMultipleEnemiesAction(
                        this.owner,
                        multiDamage,
                        DamageInfo.DamageType.THORNS,
                        AbstractGameAction.AttackEffect.NONE
                )
        );
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0] + this.amount + descriptions[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new CollateralDamagePower(this.owner, this.amount);
    }
}

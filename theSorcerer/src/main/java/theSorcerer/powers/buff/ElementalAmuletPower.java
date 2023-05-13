package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.special.IceCharm;
import theSorcerer.cards.special.FireCharm;
import theSorcerer.powers.DynamicAmountPower;

public class ElementalAmuletPower extends DynamicAmountPower {

    private Class<? extends ElementPower<?>> elementPower;

    public ElementalAmuletPower(
            AbstractCreature owner,
            int amount
    ) {
        super(
                ElementalAmuletPower.class,
                owner,
                amount
        );
        this.elementPower = null;

        updateDescription();
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (isPlayer) {
            if (DynamicDungeon.isHeated()) {
                this.elementPower = HeatedPower.class;
            }
            else if (DynamicDungeon.isChilled()) {
                this.elementPower = ChilledPower.class;
            }
            else {
                this.elementPower = null;
            }
        }
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (this.elementPower != null && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            if (this.elementPower == HeatedPower.class) {
                makeTempCharmInHand(new FireCharm());
            }
            else if (this.elementPower == ChilledPower.class) {
                makeTempCharmInHand(new IceCharm());
            }
        }
    }

    private void makeTempCharmInHand(
            final AbstractCard charmCard
    ) {
        addToBot(
                new MakeTempCardInHandAction(
                        charmCard,
                        this.amount,
                        false
                )
        );
    }

    @Override
    public void updateDescription() {
        if (this.elementPower == null) {
            this.description = descriptions[0] + this.amount + descriptions[1];
        }
        else if (this.elementPower == HeatedPower.class) {
            this.description = descriptions[2] + this.amount + descriptions[3];
        }
        else if (this.elementPower == ChilledPower.class) {
            this.description = descriptions[2] + this.amount + descriptions[4];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ElementalAmuletPower(this.owner, this.amount);
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.BUFF;
    }
}

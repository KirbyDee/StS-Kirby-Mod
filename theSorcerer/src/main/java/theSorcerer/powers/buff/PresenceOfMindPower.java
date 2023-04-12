package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.DynamicDungeon;
import theSorcerer.KirbyDeeMod;
import theSorcerer.powers.SelfRemovablePower;

public class PresenceOfMindPower extends SelfRemovablePower {

    public static final String POWER_NAME = "PresenceOfMindPower";

    public static final String POWER_ID = KirbyDeeMod.makeID(POWER_NAME);

    public PresenceOfMindPower(
            AbstractCreature owner
    ) {
        super(owner, POWER_ID);

        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (DynamicDungeon.isArcaneCard(card)) {
            flash();
        }
        else {
            removeSelf();
        }

        if (card.costForTurn > 0) {
            DynamicDungeon.gainEnergy(card.costForTurn);
        }
    }

    @Override
    public void updateDescription() {
        this.description = descriptions[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new PresenceOfMindPower(this.owner);
    }
}

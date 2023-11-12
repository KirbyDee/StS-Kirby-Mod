package theSorcerer.powers.buff;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.SelfRemovablePower;

public class PresenceOfMindPower extends SelfRemovablePower {

    public PresenceOfMindPower(
            AbstractCreature owner
    ) {
        super(
                PresenceOfMindPower.class,
                owner
        );

        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        DynamicDungeon.triggerOnPresenceOfMind();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        flash();
        if (!DynamicDungeon.isArcaneCard(card)) {
            removeSelf();
        }

        // normal cost card gives back their cost
        if (card.costForTurn > 0) {
            DynamicDungeon.gainEnergy(card.costForTurn);
        }

        // X-cost card gives back their cost
        else if (card.costForTurn == -1 && EnergyPanel.getCurrentEnergy() > 0) {
            DynamicDungeon.gainEnergy(EnergyPanel.getCurrentEnergy());
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

    @Override
    public PowerType getPowerType() {
        return PowerType.BUFF;
    }
}

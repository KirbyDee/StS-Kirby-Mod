package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theSorcerer.cards.special.StrongFireAffinity;
import theSorcerer.cards.special.StrongIceAffinity;

import java.util.ArrayList;

public class StrongAffinityAction extends AbstractGameAction {

    private final AbstractPlayer player;

    private final boolean upgraded;

    private final boolean freeToPlayOnce;

    private final int energyOnUse;

    public StrongAffinityAction(
            AbstractPlayer player,
            boolean upgraded,
            boolean freeToPlayOnce,
            int energyOnUse
    ) {
        this.player = player;
        this.upgraded = upgraded;
        this.freeToPlayOnce = freeToPlayOnce;
        this.energyOnUse = energyOnUse;
        this.actionType = ActionType.POWER;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.player.hasRelic("Chemical X")) {
            effect += 2;
            this.player.getRelic("Chemical X").flash();
        }

        if (this.upgraded) {
            ++effect;
        }

        if (effect > 0) {
            ArrayList<AbstractCard> choices = new ArrayList<>();
            AbstractCard strongFireAffinity = new StrongFireAffinity();
            initAffinityCard(strongFireAffinity, effect);
            choices.add(strongFireAffinity);
            AbstractCard strongIceAffinity = new StrongIceAffinity();
            initAffinityCard(strongIceAffinity, effect);
            choices.add(strongIceAffinity);

            addToBot(new ChooseOneAction(choices));
            if (!this.freeToPlayOnce) {
                this.player.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;
    }

    private void initAffinityCard(final AbstractCard abstractCard, final int amount) {
        abstractCard.baseMagicNumber = amount;
        abstractCard.resetAttributes();
        abstractCard.initializeDescription();
    }
}

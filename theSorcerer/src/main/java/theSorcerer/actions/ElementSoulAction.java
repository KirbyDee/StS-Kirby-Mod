package theSorcerer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.DynamicDungeon;
import theSorcerer.relics.ElementSoul;

public abstract class ElementSoulAction extends AbstractGameAction {
    private static final Logger LOG = LogManager.getLogger(ElementSoulAction.class.getName());

    private final ElementSoul relic;

    public ElementSoulAction(
            ElementSoul relic,
            int amount
    ) {
        this.relic = relic;
        this.amount = amount;
    }

    @Override
    public void update() {
        DynamicDungeon.runIfNotElementless(this::runSoulAction);
        this.isDone = true;
    }

    private void runSoulAction() {
        LOG.info("Apply Soul power at turn start");
        relic.flash();
        applySoulPower();
    }

    protected abstract void applySoulPower();
}

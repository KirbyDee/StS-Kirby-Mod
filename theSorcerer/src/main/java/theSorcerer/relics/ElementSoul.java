package theSorcerer.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;
import theSorcerer.actions.ElementSoulAction;

import java.util.function.Function;

public abstract class ElementSoul extends DynamicRelic {

    protected static final int ELEMENT_AMOUNT = 2;

    public ElementSoul(
            Class<? extends ElementSoul> thisClazz
    ) {
        super(
                thisClazz,
                RelicTier.BOSS,
                LandingSound.MAGICAL
        );
    }

    @Override
    public void atTurnStart() {
        DynamicDungeon.triggerRelic(this);
        addToBot(
                toElementSoulAction().apply(ELEMENT_AMOUNT)
        );
    }

    protected abstract Function<Integer, ElementSoulAction> toElementSoulAction();

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + ELEMENT_AMOUNT + DESCRIPTIONS[1];
    }

    @Override
    public boolean canSpawn() {
        return getOppositeSoul() == null || !AbstractDungeon.player.hasRelic(getID(getOppositeSoul()));
    }

    protected Class<? extends ElementSoul> getOppositeSoul() {
        return null;
    }
}

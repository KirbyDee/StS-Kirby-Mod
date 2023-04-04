package theSorcerer.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.actions.ElementSoulAction;

import java.util.function.Function;

public abstract class ElementSoul extends CustomRelic {
    private static final int ELEMENT_AMOUNT = 3;

    public ElementSoul(
            String id,
            Texture img,
            Texture outline
    ) {
        super(id, img, outline, RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStart() {
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
        return !AbstractDungeon.player.hasRelic(getOppositeSoulId());
    }

    protected abstract String getOppositeSoulId();
}

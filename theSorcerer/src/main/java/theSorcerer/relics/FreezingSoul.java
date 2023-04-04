package theSorcerer.relics;

import com.badlogic.gdx.graphics.Texture;
import theSorcerer.KirbyDeeMod;
import theSorcerer.actions.ElementSoulAction;
import theSorcerer.actions.FreezingSoulAction;
import theSorcerer.util.TextureLoader;

import java.util.function.Function;

import static theSorcerer.KirbyDeeMod.makeRelicOutlinePath;
import static theSorcerer.KirbyDeeMod.makeRelicPath;

public class FreezingSoul extends ElementSoul {

    public static final String ID = KirbyDeeMod.makeID("FreezingSoul");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public FreezingSoul() {
        super(ID, IMG, OUTLINE);
    }

    @Override
    protected Function<Integer, ElementSoulAction> toElementSoulAction() {
        return i -> new FreezingSoulAction(this, i);
    }

    @Override
    protected String getOppositeSoulId() {
        return BurningSoul.ID;
    }
}

package theSorcerer.relics;

import com.badlogic.gdx.graphics.Texture;
import theSorcerer.KirbyDeeMod;
import theSorcerer.actions.BurningSoulAction;
import theSorcerer.actions.ElementSoulAction;
import theSorcerer.util.TextureLoader;

import java.util.function.Function;

import static theSorcerer.KirbyDeeMod.makeRelicOutlinePath;
import static theSorcerer.KirbyDeeMod.makeRelicPath;

public class BurningSoul extends ElementSoul {

    public static final String ID = KirbyDeeMod.makeID("BurningSoul");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public BurningSoul() {
        super(ID, IMG, OUTLINE);
    }

    @Override
    protected Function<Integer, ElementSoulAction> toElementSoulAction() {
        return i -> new BurningSoulAction(this, i);
    }

    @Override
    protected String getOppositeSoulId() {
        return FreezingSoul.ID;
    }
}

package theSorcerer.relics;

import com.badlogic.gdx.graphics.Texture;
import theSorcerer.KirbyDeeMod;
import theSorcerer.actions.ElementSoulAction;
import theSorcerer.actions.EnergizedSoulAction;
import theSorcerer.util.TextureLoader;

import java.util.function.Function;

import static theSorcerer.KirbyDeeMod.makeRelicOutlinePath;
import static theSorcerer.KirbyDeeMod.makeRelicPath;

public class EnergizedSoul extends ElementSoul {

    public static final String ID = KirbyDeeMod.makeID("EnergizedSoul");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public EnergizedSoul() {
        super(ID, IMG, OUTLINE);
    }

    @Override
    protected Function<Integer, ElementSoulAction> toElementSoulAction() {
        return i -> new EnergizedSoulAction(this);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

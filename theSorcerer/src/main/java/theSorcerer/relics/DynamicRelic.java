package theSorcerer.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import theSorcerer.DynamicDungeon;
import theSorcerer.util.TextureLoader;

import static theSorcerer.KirbyDeeMod.makeRelicOutlinePath;
import static theSorcerer.KirbyDeeMod.makeRelicPath;

public abstract class DynamicRelic extends CustomRelic {

    public DynamicRelic(
            Class<? extends DynamicRelic> thisClazz,
            RelicTier tier,
            LandingSound sound
    ) {
        super(
                getID(thisClazz),
                getRelicTexture(thisClazz),
                getRelicOutlineTexture(thisClazz),
                tier,
                sound
        );
    }

    public static String getID(final Class<? extends DynamicRelic> thisClazz) {
        return DynamicDungeon.makeID(thisClazz);
    }

    public static Texture getRelicTexture(final Class<? extends DynamicRelic> thisClazz) {
        return getTexture(makeRelicPath(thisClazz.getSimpleName() + ".png"));
    }

    public static Texture getRelicOutlineTexture(final Class<? extends DynamicRelic> thisClazz) {
        return getTexture(makeRelicOutlinePath(thisClazz.getSimpleName() + ".png"));
    }

    private static Texture getTexture(final String path) {
        return TextureLoader.getTexture(path);
    }
}
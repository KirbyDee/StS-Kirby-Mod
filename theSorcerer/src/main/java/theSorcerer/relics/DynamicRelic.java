package theSorcerer.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Girya;
import com.megacrit.cardcrawl.relics.PeacePipe;
import com.megacrit.cardcrawl.relics.Shovel;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.DynamicPower;
import theSorcerer.util.TextureLoader;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    public static boolean canSpawnCampfireRelic() {
        if (AbstractDungeon.floorNum >= 48 && !Settings.isEndless) {
            return false;
        }
        else {
            return AbstractDungeon.player.relics
                    .stream()
                    .filter(DynamicRelic::isCampfireRelic)
                    .count() < 2;
        }
    }

    private static boolean isCampfireRelic(final AbstractRelic relic) {
        return relic instanceof PeacePipe ||
                relic instanceof Shovel ||
                relic instanceof Girya ||
                relic instanceof TreeOfLife;
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

    protected final void addTip(final String... keyword) {
        this.tips.addAll(
                Arrays.stream(keyword).map(DynamicDungeon::getPowerTip).collect(Collectors.toList())
        );
    }

    public void triggerOnFlashback() {}

    public void triggerOnFuturity() {}

    public void triggerOnElementless() {}

    @SafeVarargs
    protected final void addTip(final Class<? extends DynamicPower>... powerClass) {
        this.tips.addAll(
                Arrays.stream(powerClass).map(DynamicDungeon::getPowerTip).collect(Collectors.toList())
        );
    }
}

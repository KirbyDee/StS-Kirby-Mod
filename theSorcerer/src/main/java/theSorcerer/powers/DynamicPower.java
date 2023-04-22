package theSorcerer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.DynamicDungeon;
import theSorcerer.util.TextureLoader;

import static theSorcerer.KirbyDeeMod.makePowerPath;

public abstract class DynamicPower extends AbstractPower implements CloneablePowerInterface {

    private static final Logger LOG = LogManager.getLogger(DynamicPower.class.getName());

    protected final String[] descriptions;

    public DynamicPower(
            Class<? extends DynamicPower> thisClazz,
            AbstractCreature owner
    ) {
        super();
        this.ID = getID(thisClazz);
        this.owner = owner;
        this.type = getPowerType();

        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(this.ID);
        this.name = powerStrings.NAME;
        this.descriptions = powerStrings.DESCRIPTIONS;

        this.region128 = new TextureAtlas.AtlasRegion(
                getTexture(thisClazz, 84),
                0,
                0,
                84,
                84
        );
        this.region48 = new TextureAtlas.AtlasRegion(
                getTexture(thisClazz, 32),
                0,
                0,
                32,
                32
        );
    }

    public static String getID(final Class<? extends DynamicPower> thisClazz) {
        return DynamicDungeon.makeID(thisClazz);
    }

    private static Texture getTexture(final Class<? extends DynamicPower> thisClazz, final int resolution) {
        return getTexture(makePowerPath(thisClazz.getSimpleName() + resolution + ".png"));
    }

    private static Texture getTexture(final String path) {
        LOG.info("Load Texture: " + path);
        return TextureLoader.getTexture(path);
    }

    public abstract PowerType getPowerType();
}

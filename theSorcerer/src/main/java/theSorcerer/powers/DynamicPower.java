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

        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(this.ID);
        this.name = powerStrings.NAME;
        this.descriptions = powerStrings.DESCRIPTIONS;

        loadTextures(this.ID);
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




    private static Texture TEX_84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));

    private static Texture TEX_32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public DynamicPower(
            AbstractCreature owner,
            String powerID
    ) {
        this.owner = owner;
        this.ID = powerID;

        PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(this.ID);
        this.name = powerStrings.NAME;
        this.descriptions = powerStrings.DESCRIPTIONS;

        this.region128 = new TextureAtlas.AtlasRegion(TEX_84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(TEX_32, 0, 0, 32, 32);
    }

    private static void loadTextures(final String powerId) {
        if (TEX_32 == null) {
            TEX_32 = loadTexture(powerId, 32);
        }
        if (TEX_84 == null) {
            TEX_84 = loadTexture(powerId, 84);
        }
    }

    private static Texture loadTexture(final String powerId, final int resolution) {
        return getTexture(makePowerPath(powerId + resolution + ".png"));
    }

    private static Texture getTexture(final String path) {
        LOG.info("Load Texture: " + path);
        return TextureLoader.getTexture(path);
    }
}

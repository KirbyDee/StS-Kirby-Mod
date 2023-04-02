package theSorcerer.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theSorcerer.KirbyDeeMod;
import theSorcerer.powers.buff.HeatedPower;
import theSorcerer.powers.debuff.ElementlessPower;
import theSorcerer.util.TextureLoader;

import static theSorcerer.KirbyDeeMod.makeRelicOutlinePath;
import static theSorcerer.KirbyDeeMod.makeRelicPath;

public class BurningSoul extends CustomRelic {

    private static final Logger LOG = LogManager.getLogger(BurningSoul.class.getName());

    public static final String ID = KirbyDeeMod.makeID("BurningSoul");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public BurningSoul() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.MAGICAL);
    }

    // TODO: what if we have elementless, race condition?
    @Override
    public void atTurnStart() {
        if (AbstractDungeon.player.hasPower(ElementlessPower.POWER_ID)) {
            LOG.info("Cannot apply Heated due to Elementless");
            AbstractDungeon.player.getPower(ElementlessPower.POWER_ID).flash();
            return;
        }

        LOG.info("Apply Heated power at turn start");
        flash();
        addToBot(
                new ApplyPowerAction(
                        AbstractDungeon.player,
                        AbstractDungeon.player,
                        new HeatedPower(AbstractDungeon.player, 0)
                )
        );
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {
        return !AbstractDungeon.player.hasRelic(FreezingSoul.ID);
    }
}

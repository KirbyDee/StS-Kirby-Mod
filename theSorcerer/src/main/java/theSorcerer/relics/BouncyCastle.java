package theSorcerer.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;

public class BouncyCastle extends DynamicRelic {

    private static final int ENERGY_AMOUNT = 1;

    public BouncyCastle() {
        super(
                BouncyCastle.class,
                RelicTier.UNCOMMON,
                LandingSound.CLINK
        );
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        addTip("Futurity");
    }

    @Override
    public void triggerOnFuturity() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            DynamicDungeon.triggerRelic(this);
            DynamicDungeon.gainEnergy(ENERGY_AMOUNT);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

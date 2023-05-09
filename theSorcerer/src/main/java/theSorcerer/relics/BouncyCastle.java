package theSorcerer.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;

public class BouncyCastle extends DynamicRelic {

    private static final int DRAW_AMOUNT = 1;

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
        addTip("Flashback");
    }

    @Override
    public void triggerOnFlashback() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            DynamicDungeon.triggerRelic(this);
            DynamicDungeon.drawCard(DRAW_AMOUNT);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DRAW_AMOUNT + DESCRIPTIONS[1];
    }
}

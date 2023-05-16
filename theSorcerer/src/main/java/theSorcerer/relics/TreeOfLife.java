package theSorcerer.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import theSorcerer.DynamicDungeon;
import theSorcerer.ui.campfire.ArcanemorphoseOption;

import java.util.ArrayList;

public class TreeOfLife extends DynamicRelic {

    public TreeOfLife() {
        super(
                TreeOfLife.class,
                RelicTier.RARE,
                LandingSound.FLAT
        );
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        addTip("Arcanemorphose");
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public boolean canSpawn() {
        return canSpawnCampfireRelic();
    }

    @Override
    public void addCampfireOption(ArrayList<AbstractCampfireOption> options) {
        options.add(
                new ArcanemorphoseOption(
                        !DynamicDungeon.filterCardGroupBy(
                                AbstractDungeon.player.masterDeck,
                                DynamicDungeon::canMakeCardArcane
                        ).isEmpty()
                )
        );
    }
}

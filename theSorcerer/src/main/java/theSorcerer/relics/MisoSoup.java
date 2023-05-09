package theSorcerer.relics;

import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;

public class MisoSoup extends DynamicRelic {

    public MisoSoup() {
        super(
                MisoSoup.class,
                RelicTier.RARE,
                LandingSound.FLAT
        );
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        addTip("Firemorphose", "Icemorphose");
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
        options.add(null); // TODO: check how TokeOption goes
    }
}

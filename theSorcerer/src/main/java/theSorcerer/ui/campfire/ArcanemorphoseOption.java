package theSorcerer.ui.campfire;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import theSorcerer.effect.CampfireArcanemorphoseEffect;

import static theSorcerer.KirbyDeeMod.makeUiPath;

public class ArcanemorphoseOption extends AbstractCampfireOption {

    private static final String[] TEXT = new String[] {"Arcanemorphose", "Change a card in your deck to become Arcane."}; // TODOO

    public ArcanemorphoseOption(boolean active) {
        this.usable = active;
        this.label = TEXT[0];
        this.description = TEXT[1];
        this.img = ImageMaster.loadImage(getImagePath());
    }

    private static String getImagePath() {
        return makeUiPath("campfire/arcanemorphose.png");
    }

    public void useOption() {
        if (this.usable) {
            AbstractDungeon.effectList.add(new CampfireArcanemorphoseEffect());
        }
    }
}

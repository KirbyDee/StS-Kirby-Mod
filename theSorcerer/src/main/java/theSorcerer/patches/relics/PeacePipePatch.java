package theSorcerer.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.relics.PeacePipe;
import theSorcerer.relics.DynamicRelic;

@SpirePatch(clz= PeacePipe.class, method="canSpawn")
public class PeacePipePatch {

    public static boolean Replace(PeacePipe self) {
        return DynamicRelic.canSpawnCampfireRelic();
    }
}

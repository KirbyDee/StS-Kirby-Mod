package theSorcerer.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.relics.Shovel;
import theSorcerer.relics.DynamicRelic;

@SpirePatch(clz= Shovel.class, method="canSpawn")
public class ShovelPatch {

    public static boolean Replace(Shovel self) {
        return DynamicRelic.canSpawnCampfireRelic();
    }
}

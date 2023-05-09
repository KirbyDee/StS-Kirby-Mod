package theSorcerer.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.relics.Girya;
import theSorcerer.relics.DynamicRelic;

@SpirePatch(clz= Girya.class, method="canSpawn")
public class GiryaPatch {

    public static boolean Replace(Girya self) {
        return DynamicRelic.canSpawnCampfireRelic();
    }
}

package theSorcerer.patches.core;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.debuff.IceAgePower;

@SpirePatch(clz = AbstractCreature.class, method = SpirePatch.CLASS)
public class AbstractCreaturePatch {

    @SpirePatch(clz = AbstractCreature.class, method = "addBlock")
    public static class UseCardPatch {

        public static void Postfix(AbstractCreature self, int blockAmount) {
            if (!self.isPlayer && blockAmount > 0) {
                DynamicDungeon.withPowerDo(
                        self,
                        IceAgePower.class,
                        p -> p.onGainedBlock(blockAmount)
                );
            }
        }
    }
}
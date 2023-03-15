package theSorcerer.patches.monsters;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;

@SpirePatch(clz = AbstractMonster.class, method = SpirePatch.CLASS)
public class AbstractMonsterPatch {

    public static SpireField<EnemyMoveInfo> moveInfo = new SpireField<>(() -> null);

    @SpirePatch(clz = AbstractMonster.class, method = "createIntent")
    public static class CreateIntentPatch {

        public static void Postfix(AbstractMonster self, EnemyMoveInfo ___move) {
            moveInfo.set(self, ___move);
        }
    }
}
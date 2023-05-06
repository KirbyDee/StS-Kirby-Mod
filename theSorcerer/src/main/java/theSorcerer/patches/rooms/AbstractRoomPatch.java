package theSorcerer.patches.rooms;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theSorcerer.patches.cards.AbstractCardPatch;

@SpirePatch(clz = AbstractRoom.class, method = SpirePatch.CLASS)
public class AbstractRoomPatch {

    @SpirePatch(clz = AbstractRoom.class, method = "endBattle")
    public static class EndBattlePatch {

        public static void Prefix(
                AbstractRoom self
        ) {
            AbstractDungeon.player.masterDeck.group
                    .forEach(c -> AbstractCardPatch.abilitiesPerCombat.get(c).clear());
        }
    }
}
package theSorcerer.patches.characters;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

@SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
public class AbstractPlayerPatch {

    public static SpireField<Integer> elementalCardsPlayedPerCombat = new SpireField<>(() -> 0);

    public static SpireField<Integer> arcaneCardsPlayedPerCombat = new SpireField<>(() -> 0);
}
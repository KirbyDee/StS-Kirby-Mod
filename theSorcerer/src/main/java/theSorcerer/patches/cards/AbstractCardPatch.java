package theSorcerer.patches.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;
import java.util.List;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class AbstractCardPatch {

    public static SpireField<List<CardAbility>> abilities = new SpireField<>(ArrayList::new);

    @SpirePatch(clz = AbstractCard.class, method = "makeStatEquivalentCopy")
    public static class MakeStatEquivalentCopyPatch {

        public static AbstractCard Postfix(AbstractCard result, AbstractCard self) {
            result.isEthereal = self.isEthereal;
            result.isInnate = self.isInnate;
            result.retain = self.retain;
            result.exhaust = self.exhaust;
            result.tags = self.tags;
//            AbstractCardPatch.abilities.set(result, AbstractCardPatch.abilities.get(self)); // TODO: upgrade card will not have the same abilities as before?

            return result;
        }
    }
}
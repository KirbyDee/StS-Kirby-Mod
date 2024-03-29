package theSorcerer.patches.screens.select;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;

import java.util.function.Consumer;

@SpirePatch(clz = GridCardSelectScreen.class, method = SpirePatch.CLASS)
public class GridCardSelectScreenPatch {

    private static final String CANCEL = "Cancel";

    public static SpireField<Consumer<AbstractCard>> forModifyCardConsumer = new SpireField<>(() -> null);

    public static void open(
            final CardGroup group,
            final int numCards,
            final String tipMsg,
            final boolean forUpgrade,
            final boolean forTransform,
            final boolean canCancel,
            final boolean forPurge,
            final Consumer<AbstractCard> applyModificationToCard
    ) {
        AbstractDungeon.gridSelectScreen.open(
                group,
                numCards,
                tipMsg,
                forUpgrade,
                forTransform,
                canCancel,
                forPurge
        );
        forModifyCardConsumer.set(AbstractDungeon.gridSelectScreen, applyModificationToCard);
        if (canCancel && applyModificationToCard != null) {
            AbstractDungeon.overlayMenu.cancelButton.show(CANCEL);
        }
    }

    @SpirePatch(
            clz = GridCardSelectScreen.class,
            method = "open",
            paramtypez={
                    CardGroup.class,
                    int.class,
                    String.class,
                    boolean.class,
                    boolean.class,
                    boolean.class,
                    boolean.class
            }
    )
    public static class OpenPatch {

        public static void Prefix(
                GridCardSelectScreen self,
                CardGroup group,
                int numCards,
                String tipMsg,
                boolean forUpgrade,
                boolean forTransform,
                boolean canCancel,
                boolean forPurge
        ) {
            forModifyCardConsumer.set(self, null);
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "callOnOpen")
    public static class PrepPatch {

        public static void Prefix(
                GridCardSelectScreen self
        ) {
            forModifyCardConsumer.set(self, null);
        }
    }

    @SpirePatch(clz = GridCardSelectScreen.class, method = "cancelUpgrade")
    public static class CancelUpgradePatch {

        public static void Prefix(
                GridCardSelectScreen self,
                boolean ___canCancel
        ) {
            Consumer<AbstractCard> applyModificationToCard = GridCardSelectScreenPatch.forModifyCardConsumer.get(self);
            if (self.upgradePreviewCard == null || applyModificationToCard == null) {
                return;
            }

            if (___canCancel) {
                AbstractDungeon.overlayMenu.cancelButton.show(CANCEL);
            }
        }
    }
}
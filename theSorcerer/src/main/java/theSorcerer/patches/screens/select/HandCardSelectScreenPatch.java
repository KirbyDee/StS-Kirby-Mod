package theSorcerer.patches.screens.select;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import theSorcerer.DynamicDungeon;

import java.util.function.Consumer;

@SpirePatch(clz = HandCardSelectScreen.class, method = SpirePatch.CLASS)
public class HandCardSelectScreenPatch {

    public static SpireField<Consumer<AbstractCard>> forElementMorphoseField = new SpireField<>(() -> null);

    public static void open(
            String msg,
            int amount,
            boolean anyNumber,
            boolean canPickZero,
            boolean forTransform,
            boolean forUpgrade,
            Consumer<AbstractCard> applyElementToCard
    ) {
        AbstractDungeon.handCardSelectScreen.open(
                msg,
                amount,
                anyNumber,
                canPickZero,
                forTransform,
                forUpgrade
        );
        forElementMorphoseField.set(AbstractDungeon.handCardSelectScreen, applyElementToCard);
    }

    @SpirePatch(
            clz = HandCardSelectScreen.class,
            method = "open",
            paramtypez={
                    String.class,
                    int.class,
                    boolean.class,
                    boolean.class,
                    boolean.class,
                    boolean.class,
                    boolean.class
            }
    )
    public static class OpenPatch {

        public static void Prefix(
                HandCardSelectScreen self,
                String msg,
                int amount,
                boolean anyNumber,
                boolean canPickZero,
                boolean forTransform,
                boolean forUpgrade,
                boolean upTo
        ) {
            forElementMorphoseField.set(self, null);
        }
    }

    @SpirePatch(clz = HandCardSelectScreen.class, method = "prep")
    public static class PrepPatch {

        public static void Prefix(
                HandCardSelectScreen self
        ) {
            forElementMorphoseField.set(self, null);
        }
    }

    @SpirePatch(clz = HandCardSelectScreen.class, method = "refreshSelectedCards")
    public static class RefreshSelectedCardsPatch {

        public static void Postfix(
                HandCardSelectScreen self
        ) {
            Consumer<AbstractCard> applyElementToCard = forElementMorphoseField.get(self);
            if (applyElementToCard == null) {
                return;
            }

            if (self.selectedCards.size() == 1) {
                self.selectedCards.group.get(0).target_x = (float) Settings.WIDTH * 0.37F;
            }
        }
    }

    @SpirePatch(clz = HandCardSelectScreen.class, method = "selectHoveredCard")
    public static class SelectHoveredCardPatch {

        public static void Postfix(
                HandCardSelectScreen self
        ) {
            Consumer<AbstractCard> applyElementToCard = forElementMorphoseField.get(self);
            if (applyElementToCard == null) {
                if (self.upgradePreviewCard != null) {
                    DynamicDungeon.updateAbilityDescription(self.upgradePreviewCard);
                }
                return;
            }

            if (self.numCardsToSelect == 1 && self.selectedCards.group.size() == 1 && self.selectedCards.size() == 1) {
                self.upgradePreviewCard = self.selectedCards.group.get(0).makeStatEquivalentCopy();
                applyElementToCard.accept(self.upgradePreviewCard);
                DynamicDungeon.updateAbilityDescription(self.upgradePreviewCard);
                self.upgradePreviewCard.drawScale = 0.75F;
            }
        }
    }

    @SpirePatch(clz = HandCardSelectScreen.class, method = "updateSelectedCards")
    public static class UpdateSelectedCardsPatch {

        public static void Postfix(
                HandCardSelectScreen self
        ) {
            Consumer<AbstractCard> applyElementToCard = forElementMorphoseField.get(self);
            if (applyElementToCard == null) {
                return;
            }

            for (AbstractCard e : self.selectedCards.group) {
                if (self.selectedCards.group.size() < 5) {
                    e.targetDrawScale = 0.75F;
                    if (Math.abs(e.current_x - e.target_x) < HandCardSelectScreen.MIN_HOVER_DIST && e.hb.hovered) {
                        e.targetDrawScale = 0.85F;
                    }
                }
            }
        }
    }

    @SpirePatch(clz = HandCardSelectScreen.class, method = "updateMessage")
    public static class UpdateMessagePatch {

        public static void Postfix(
                HandCardSelectScreen self
        ) {
            Consumer<AbstractCard> applyElementToCard = forElementMorphoseField.get(self);
            if (applyElementToCard == null) {
                if (self.upgradePreviewCard != null) {
                    DynamicDungeon.updateAbilityDescription(self.upgradePreviewCard);
                }
                return;
            }

            if (self.selectedCards.group.size() != 0 && self.selectedCards.group.size() == self.numCardsToSelect && self.selectedCards.size() == 1) {
                if (self.upgradePreviewCard == null) {
                    self.upgradePreviewCard = self.selectedCards.group.get(0).makeStatEquivalentCopy();
                }
                applyElementToCard.accept(self.upgradePreviewCard);
                DynamicDungeon.updateAbilityDescription(self.upgradePreviewCard);
                self.upgradePreviewCard.drawScale = 0.75F;
                self.upgradePreviewCard.targetDrawScale = 0.75F;
            }
        }
    }
}
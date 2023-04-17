package theSorcerer.patches.screens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theSorcerer.DynamicDungeon;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class AbstractPileViewScreenPatch {

    public static void UpdatePatch(
            CardGroup cardGroup,
            Predicate<AbstractCard> filter,
            Consumer<AbstractCard> consumer
    ) {
        if (AbstractDungeon.player.hand.size() > 10) {
            AbstractDungeon.player.createHandIsFullDialog();
            return;
        }

        cardGroup.group
                .stream()
                .filter(filter)
                .filter(AbstractPileViewScreenPatch::duringPlayerTurnAndGlow)
                .filter(AbstractPileViewScreenPatch::isHovered)
                .filter(c -> InputHelper.justClickedLeft)
                .findAny()
                .ifPresent(consumer);
    }

    public static boolean duringPlayerTurnAndGlow(AbstractCard card) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.actionManager.turnHasEnded) {
            if (!card.isGlowing) {
                card.beginGlowing();
            }
            return true;
        }
        else {
            if (card.isGlowing) {
                card.stopGlowing();
            }
            return false;
        }
    }

    private static boolean isHovered(AbstractCard card) {
        return card.hb.hovered;
    }

    public static void computeDescription(AbstractCard card) {
        // remove futurity / flashback
        AbstractCardPatch.abilities.get(card).remove(CardAbility.FLASHBACK);
        AbstractCardPatch.abilities.get(card).remove(CardAbility.FUTURITY);

        // add ethereal
        card.isEthereal = true;
        AbstractCardPatch.abilities.get(card).add(CardAbility.ETHEREAL);

        // add exhaust (if not power, doesn't make sense to give exhaust to powers)
        if (card.type != AbstractCard.CardType.POWER) {
            card.exhaust = true;
            AbstractCardPatch.abilities.get(card).add(CardAbility.EXHAUST);
        }
        DynamicDungeon.updateAbilityDescription(card);
    }
}
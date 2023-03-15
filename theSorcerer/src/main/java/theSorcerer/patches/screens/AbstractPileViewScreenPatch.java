package theSorcerer.patches.screens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class AbstractPileViewScreenPatch {

    public static void OpenPatch(
            CardGroup cardGroup,
            Predicate<AbstractCard> filter
    ) {
        cardGroup.group
                .stream()
                .filter(filter)
                .forEach(AbstractPileViewScreenPatch::startGlowing);
    }

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
                .filter(AbstractPileViewScreenPatch::isHovered)
                .filter(c -> InputHelper.justClickedLeft)
                .findAny()
                .ifPresent(consumer);
    }

    public static void startGlowing(AbstractCard card) {
        if (!card.isGlowing) {
            card.beginGlowing();
        }
    }

    private static boolean isHovered(AbstractCard card) {
        return card.hb.hovered;
    }

    public static void computeDescription(AbstractCard card) {
        // remove futurity / flashback
        AbstractCardPatch.abilities.get(card).remove(CardAbility.FLASHBACK);
        AbstractCardPatch.abilities.get(card).remove(CardAbility.FUTURITY);
        card.tags.remove(SorcererCardTags.FUTURITY);
        card.tags.remove(SorcererCardTags.FLASHBACK);

        // add ethereal
        card.isEthereal = true;
        AbstractCardPatch.abilities.get(card).add(CardAbility.ETHEREAL);

        // add exhaust (if not power, doesn't make sense to give exhaust to powers)
        if (card.type != AbstractCard.CardType.POWER) {
            card.exhaust = true;
            AbstractCardPatch.abilities.get(card).add(CardAbility.EXHAUST);
        }

        // in case of non-Dynamic Cards
        if (!(card instanceof DynamicCard)) {
            // we have to remove possible EXHAUST/ETHEREAL from raw description
            CardAbility.EXHAUST.removeDescription(card);
            CardAbility.ETHEREAL.removeDescription(card);

            // add all correct abilities again
            AbstractCardPatch.abilities.get(card).forEach(a -> a.addDescription(card));
        }

        // update shown description
        card.initializeDescription();
    }
}
package theSorcerer.patches.screens;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.DrawPileViewScreen;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class AbstractPileViewScreenPatch {

    public static void Postfix(
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
                .peek(AbstractPileViewScreenPatch::startGlowing)
                .filter(AbstractPileViewScreenPatch::isHovered)
                .filter(c -> InputHelper.justClickedLeft)
                .findAny()
                .ifPresent(consumer);
    }

    private static void startGlowing(AbstractCard card) {
        if (!card.isGlowing) {
            card.beginGlowing();
        }
    }

    private static boolean isHovered(AbstractCard card) {
        return card.hb.hovered;
    }

    public static void computeDescription(AbstractCard card) {
        // remove futurity / flashback
        AbstractCardPatch.futurity.set(card, false);
        AbstractCardPatch.flashback.set(card, false);
        AbstractCardPatch.abilities.get(card).remove(CardAbility.FLASHBACK);
        AbstractCardPatch.abilities.get(card).remove(CardAbility.FUTURITY);
        card.tags.remove(SorcererCardTags.FUTURITY);
        card.tags.remove(SorcererCardTags.FLASHBACK);

        // add ethereal
        card.isEthereal = true;
        AbstractCardPatch.abilities.get(card).add(CardAbility.ETHEREAL);

        // add exhaust
        card.exhaust = true;
        AbstractCardPatch.abilities.get(card).add(CardAbility.EXHAUST);

        // reset raw description to remove added abilities
        card.rawDescription = AbstractCardPatch.baseRawDescription.get(card);

        // in case of non-Dynamic Cards, we have to remove possible EXHAUST/ETHEREAL from raw description
        CardAbility.EXHAUST.removeDescription(card);
        CardAbility.ETHEREAL.removeDescription(card);

        // add all correct abilities again
        AbstractCardPatch.abilities.get(card).forEach(a -> a.addDescription(card));

        // update shown description
        card.initializeDescription();
    }
}
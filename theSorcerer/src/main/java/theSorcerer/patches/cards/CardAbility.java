package theSorcerer.patches.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.cards.SorcererCardTags;

import java.util.Arrays;

public enum CardAbility {
    EMPTY("", AbstractCard.CardTags.EMPTY, CardAbilityFix.PREFIX),
    FIRE(CardUtil.MOD_PREFIX + "Fire", SorcererCardTags.FIRE, CardAbilityFix.PREFIX),
    ICE(CardUtil.MOD_PREFIX + "Ice", SorcererCardTags.ICE, CardAbilityFix.PREFIX),
    AUTO(CardUtil.MOD_PREFIX + "Auto", AbstractCard.CardTags.EMPTY, CardAbilityFix.PREFIX),
    UNPLAYABLE("Unplayable", AbstractCard.CardTags.EMPTY, CardAbilityFix.PREFIX),
    ETHEREAL("Ethereal", AbstractCard.CardTags.EMPTY, CardAbilityFix.PREFIX),
    RETAIN("Retain", AbstractCard.CardTags.EMPTY, CardAbilityFix.PREFIX),
    INNATE("Innate", AbstractCard.CardTags.EMPTY, CardAbilityFix.PREFIX),
    ENTOMB(CardUtil.MOD_PREFIX + "Entomb", AbstractCard.CardTags.EMPTY, CardAbilityFix.PREFIX),
    EXHAUST("Exhaust", AbstractCard.CardTags.EMPTY, CardAbilityFix.POSTFIX),
    FUTURITY(CardUtil.MOD_PREFIX + "Futurity", SorcererCardTags.FUTURITY, CardAbilityFix.POSTFIX),
    FLASHBACK(CardUtil.MOD_PREFIX + "Flashback", SorcererCardTags.FLASHBACK, CardAbilityFix.POSTFIX);

    public final String text;

    public final AbstractCard.CardTags tag;

    public final CardAbilityFix fix;

    CardAbility(final String text, final AbstractCard.CardTags tag, final CardAbilityFix fix) {
        this.text = text;
        this.tag = tag;
        this.fix = fix;
    }

    public static CardAbility from(AbstractCard.CardTags tag) {
        return Arrays.stream(CardAbility.values())
                .filter(a -> tag == a.tag)
                .findFirst()
                .orElse(EMPTY);
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public void addDescription(final AbstractCard card) {
        if (fix == CardAbilityFix.PREFIX) {
            card.rawDescription = text + CardUtil.PERIOD + CardUtil.NEW_LINE + card.rawDescription;
        }
        else {
            card.rawDescription = card.rawDescription + CardUtil.NEW_LINE + text + CardUtil.PERIOD;
        }
    }

    public void removeDescription(final AbstractCard card) {
        if (fix == CardAbilityFix.PREFIX) {
            card.rawDescription = card.rawDescription.replace(text + CardUtil.PERIOD + CardUtil.NEW_LINE, "");
        }
        else {
            card.rawDescription = card.rawDescription.replace(CardUtil.NEW_LINE + text + CardUtil.PERIOD, "");
        }
    }
}

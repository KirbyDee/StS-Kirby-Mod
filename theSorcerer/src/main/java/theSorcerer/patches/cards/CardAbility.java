package theSorcerer.patches.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.Arrays;

public enum CardAbility {
    // TODOO: TExt to localization
    EMPTY("", CardAbilityFix.NONE),
    ARCANE(CardUtil.MOD_PREFIX + "Arcane", CardAbilityFix.PREFIX),
    FIRE(CardUtil.MOD_PREFIX + "Fire", CardAbilityFix.PREFIX),
    ICE(CardUtil.MOD_PREFIX + "Ice", CardAbilityFix.PREFIX),
    AUTO(CardUtil.MOD_PREFIX + "Auto", CardAbilityFix.PREFIX),
    UNPLAYABLE("Unplayable", CardAbilityFix.PREFIX),
    ETHEREAL("Ethereal", CardAbilityFix.PREFIX),
    RETAIN("Retain", CardAbilityFix.PREFIX),
    INNATE("Innate", CardAbilityFix.PREFIX),
    ENTOMB(CardUtil.MOD_PREFIX + "Entomb", CardAbilityFix.PREFIX),
    EXHAUST("Exhaust", CardAbilityFix.POSTFIX),
    FUTURITY(CardUtil.MOD_PREFIX + "Futurity", CardAbilityFix.POSTFIX),
    FLASHBACK(CardUtil.MOD_PREFIX + "Flashback", CardAbilityFix.POSTFIX);

    public final String text;

    public final CardAbilityFix fix;

    CardAbility(final String text, final CardAbilityFix fix) {
        this.text = text;
        this.fix = fix;
    }

    public static void removeAllAbilityRawDescriptions(final AbstractCard card) {
        Arrays.stream(CardAbility.values())
                .forEach(a -> a.removeRawDescription(card));
    }

    public static void initializeAbilityRawDescriptions(final AbstractCard card) {
        removeAllAbilityRawDescriptions(card);
        AbstractCardPatch.abilitiesPerCombat.get(card)
                .forEach(a -> a.addRawDescription(card));
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public void addRawDescription(final AbstractCard card) {
        if (fix == CardAbilityFix.PREFIX) {
            card.rawDescription = text + CardUtil.PERIOD + CardUtil.NEW_LINE + card.rawDescription;
        }
        else {
            card.rawDescription = card.rawDescription + CardUtil.NEW_LINE + text + CardUtil.PERIOD;
        }
    }

    public void removeRawDescription(final AbstractCard card) {
        if (fix == CardAbilityFix.PREFIX) {
            card.rawDescription = card.rawDescription.replace(text + CardUtil.PERIOD + CardUtil.NEW_LINE, "");
        }
        else {
            card.rawDescription = card.rawDescription.replace(CardUtil.NEW_LINE + text + CardUtil.PERIOD, "");
        }
    }
}

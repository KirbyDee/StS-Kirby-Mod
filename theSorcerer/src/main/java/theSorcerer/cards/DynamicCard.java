package theSorcerer.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.KirbyDeeMod;

import java.util.*;

public abstract class DynamicCard extends CustomCard {

    public static String MOD_PREFIX = "thesorcerer:";

    public static final String NEW_LINE = " NL ";

    public static final String PERIOD = ".";

    public enum CardAbilityFix {

        PREFIX,
        POSTFIX
    }


    public enum CardAbility {
        FIRE(MOD_PREFIX + "Fire", CardAbilityFix.PREFIX),
        ICE(MOD_PREFIX + "Ice", CardAbilityFix.PREFIX),
        UNPLAYABLE("Unplayable", CardAbilityFix.PREFIX),
        ETHEREAL("Ethereal", CardAbilityFix.PREFIX),
        RETAIN("Retain", CardAbilityFix.PREFIX),
        INNATE("Innate", CardAbilityFix.PREFIX),
        EXHAUST("Exhaust", CardAbilityFix.POSTFIX),
        FLASHBACK(MOD_PREFIX + "Flashback", CardAbilityFix.POSTFIX);

        public final String text;

        public final CardAbilityFix fix;

        CardAbility(final String text, final CardAbilityFix fix) {
            this.text = text;
            this.fix = fix;
        }
    }

    protected final List<CardAbility> abilities = new ArrayList<>();

    public int baseSecondMagicNumber;

    public int secondMagicNumber;

    public boolean upgradedSecondMagicNumber;

    public boolean isSecondMagicNumberModified;

    public boolean unplayable;

    public boolean flashback;

    public static String getID(Class<? extends DynamicCard> thisClazz) {
        return KirbyDeeMod.makeID(thisClazz.getSimpleName());
    }

    public static DynamicCard.InfoBuilder InfoBuilder(Class<? extends DynamicCard> thisClazz) {
        return new DynamicCard.InfoBuilder(thisClazz);
    }

    public static class InfoBuilder {

        private final Class<? extends DynamicCard> thisClazz;

        private final Set<CardTags> tags = new HashSet<>();

        private final Set<CardAbility> abilities = new HashSet<>();

        private CardType type = CardType.STATUS;

        private CardRarity rarity = CardRarity.BASIC;

        private CardTarget target = CardTarget.NONE;

        private CardColor color = CardColor.COLORLESS;

        private int cost = -2;

        public InfoBuilder(
                Class<? extends DynamicCard> thisClazz
        ) {
            this.thisClazz = thisClazz;
        }

        public InfoBuilder type(final CardType type) {
            this.type = type;
            return this;
        }

        public InfoBuilder rarity(final CardRarity rarity) {
            this.rarity = rarity;
            return this;
        }

        public InfoBuilder target(final CardTarget target) {
            this.target = target;
            return this;
        }

        public InfoBuilder color(final CardColor color) {
            this.color = color;
            return this;
        }

        public InfoBuilder tags(final CardTags... tags) {
            this.tags.addAll(Arrays.asList(tags));
            if (this.tags.contains(SorcererCardTags.FIRE)) {
                this.abilities.add(CardAbility.FIRE);
            }
            if (this.tags.contains(SorcererCardTags.ICE)) {
                this.abilities.add(CardAbility.ICE);
            }
            return this;
        }

        public InfoBuilder abilities(final CardAbility... abilities) {
            this.abilities.addAll(Arrays.asList(abilities));
            if (this.abilities.contains(CardAbility.FIRE)) {
                this.tags.add(SorcererCardTags.FIRE);
            }
            if (this.abilities.contains(CardAbility.ICE)) {
                this.tags.add(SorcererCardTags.ICE);
            }
            return this;
        }

        public InfoBuilder cost(final int cost) {
            this.cost = cost;
            return this;
        }

        public Info build() {
            String id = getID(this.thisClazz);
            CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(id);
            String img = KirbyDeeMod.makeCardPath(this.type, this.thisClazz.getSimpleName());
            return new Info(id, cardStrings, img, this);
        }
    }

    private static class Info {

        public final String id;

        public final CardStrings cardStrings;

        public final String img;

        public final Class<? extends DynamicCard> thisClazz;

        public final Set<CardTags> tags;

        public final Set<CardAbility> abilities;

        public final CardType type;

        public final CardRarity rarity;

        public final CardTarget target;

        private final CardColor color;

        public final int cost;

        public Info(
                final String id,
                final CardStrings cardStrings,
                final String img,
                final InfoBuilder builder
        ) {
            this.id = id;
            this.cardStrings = cardStrings;
            this.img = img;
            this.thisClazz = builder.thisClazz;
            this.tags = builder.tags;
            this.abilities = builder.abilities;
            this.type = builder.type;
            this.rarity = builder.rarity;
            this.target = builder.target;
            this.color = builder.color;
            this.cost = builder.cost;
        }
    }

    public DynamicCard(
            Info info
    ) {
        super(
                info.id,
                info.cardStrings.NAME,
                info.img,
                info.cost,
                info.cardStrings.DESCRIPTION,
                info.type,
                info.color,
                info.rarity,
                info.target
        );
        this.tags.addAll(info.tags);
        this.abilities.addAll(info.abilities);
        this.unplayable = info.abilities.contains(CardAbility.UNPLAYABLE);
        this.isEthereal = info.abilities.contains(CardAbility.ETHEREAL);
        this.isInnate = info.abilities.contains(CardAbility.INNATE);
        this.retain = info.abilities.contains(CardAbility.RETAIN);
        this.flashback = info.abilities.contains(CardAbility.FLASHBACK);
        this.exhaust = info.abilities.contains(CardAbility.EXHAUST);

        updateDescription();
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (this.upgradedSecondMagicNumber) {
            this.secondMagicNumber = this.baseSecondMagicNumber;
            this.isSecondMagicNumberModified = true;
        }

    }

    public void upgradeSecondMagicNumber(int amount) {
        this.baseSecondMagicNumber += amount;
        this.secondMagicNumber =  this.baseSecondMagicNumber;
        this.upgradedSecondMagicNumber = true;
    }

    private void updateDescription() {
        this.abilities.forEach(this::computeDescription);
    }

    private void computeDescription(final CardAbility ability) {
        if (ability.fix == CardAbilityFix.PREFIX) {
            this.rawDescription = ability.text + PERIOD + NEW_LINE + this.rawDescription;
        }
        else {
            this.rawDescription = this.rawDescription + NEW_LINE + ability.text + PERIOD;
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return !this.unplayable && super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeValues();
            initializeDescription();
        }
    }

    protected void upgradeValues() {}
}

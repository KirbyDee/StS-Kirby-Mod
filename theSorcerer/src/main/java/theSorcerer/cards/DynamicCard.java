package theSorcerer.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.KirbyDeeMod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class DynamicCard extends CustomCard {

    public static final String NEW_LINE = " NL ";

    public static final String MOD_PREFIX = "thesorcerer:";

    public static final String ICE_CARD_PREFIX = "Ice";

    public static final String FIRE_CARD_PREFIX = "Fire";

    public static final String UNPLAYABLE_PREFIX = "Unplayable.";

    public static final String ETHEREAL_PREFIX = "Ethereal.";

    public static final String INNATE_PREFIX = "Innate.";

    public static final String RETAIN_PREFIX = "Retain.";

    public static final String FLASHBACK_POSTFIX = "Flashback.";

    public static final String EXHAUST_POSTFIX = "Exhaust.";

    protected boolean unplayable;

    protected boolean flashback;

    public static String getID(Class<? extends DynamicCard> thisClazz) {
        return KirbyDeeMod.makeID(thisClazz.getSimpleName());
    }

    public static DynamicCard.InfoBuilder InfoBuilder(Class<? extends DynamicCard> thisClazz) {
        return new DynamicCard.InfoBuilder(thisClazz);
    }

    public static class InfoBuilder {

        private final Class<? extends DynamicCard> thisClazz;

        private final List<CardTags> tags = new ArrayList<>();

        private CardType type = CardType.STATUS;

        private CardRarity rarity = CardRarity.BASIC;

        private CardTarget target = CardTarget.NONE;

        private CardColor color = CardColor.COLORLESS;

        private int cost = -2;

        private boolean unplayable = false;

        private boolean ethereal = false;

        private boolean innate = false;

        private boolean retain = false;

        private boolean flashback = false;

        private boolean exhaust = false;

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
            return this;
        }

        public InfoBuilder cost(final int cost) {
            this.cost = cost;
            return this;
        }

        public InfoBuilder unplayable(final boolean unplayable) {
            this.unplayable = unplayable;
            return this;
        }

        public InfoBuilder ethereal(final boolean ethereal) {
            this.ethereal = ethereal;
            return this;
        }

        public InfoBuilder innate(final boolean innate) {
            this.innate = innate;
            return this;
        }

        public InfoBuilder retain(final boolean retain) {
            this.retain = retain;
            return this;
        }

        public InfoBuilder flashback(final boolean flashback) {
            this.flashback = flashback;
            return this;
        }

        public InfoBuilder exhaust(final boolean exhaust) {
            this.exhaust = exhaust;
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

        public final List<CardTags> tags;

        public final CardType type;

        public final CardRarity rarity;

        public final CardTarget target;

        private final CardColor color;

        public final int cost;

        public final boolean unplayable;

        public final boolean ethereal;

        public final boolean innate;

        public final boolean retain;

        public final boolean flashback;

        public final boolean exhaust;

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
            this.type = builder.type;
            this.rarity = builder.rarity;
            this.target = builder.target;
            this.color = builder.color;
            this.cost = builder.cost;
            this.unplayable = builder.unplayable;
            this.ethereal = builder.ethereal;
            this.innate = builder.innate;
            this.retain = builder.retain;
            this.flashback = builder.flashback;
            this.exhaust = builder.exhaust;
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
        this.unplayable = info.unplayable;
        this.isEthereal = info.ethereal;
        this.isInnate = info.innate;
        this.retain = info.retain;
        this.flashback = info.flashback;
        this.exhaust = info.exhaust;

        updateDescription();
    }

    private void updateDescription() {
        if (this.hasTag(SorcererCardTags.FIRE)) {
            addPrefixDescription(MOD_PREFIX + FIRE_CARD_PREFIX);
        }
        else if (this.hasTag(SorcererCardTags.ICE)) {
            addPrefixDescription(MOD_PREFIX + ICE_CARD_PREFIX);
        }
        if (this.unplayable) {
            addPrefixDescription(UNPLAYABLE_PREFIX);
        }
        if (this.isEthereal) {
            addPrefixDescription(ETHEREAL_PREFIX);
        }
        if (this.isInnate) {
            addPrefixDescription(INNATE_PREFIX);
        }
        if (this.retain) {
            addPrefixDescription(RETAIN_PREFIX);
        }
        if (this.flashback) {
            addPostfixDescription(FLASHBACK_POSTFIX);
        }
        if (this.exhaust) {
            addPostfixDescription(EXHAUST_POSTFIX);
        }
    }

    private void addPrefixDescription(final String prefix) {
        this.rawDescription = prefix + NEW_LINE + this.rawDescription;
    }

    private void addPostfixDescription(final String postfix) {
        this.rawDescription = this.rawDescription + NEW_LINE + postfix;
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

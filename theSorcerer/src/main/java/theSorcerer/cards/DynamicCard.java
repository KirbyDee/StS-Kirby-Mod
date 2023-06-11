package theSorcerer.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import org.apache.commons.lang3.StringUtils;
import theSorcerer.DynamicDungeon;
import theSorcerer.KirbyDeeMod;
import theSorcerer.modifiers.CardModifier;
import theSorcerer.util.ElementAmount;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class DynamicCard extends CustomCard {

    private String baseRawDescription;

    private String baseUpgradeRawDescription;

    protected String[] baseExtendedDescriptions;

    public int baseSecondMagicNumber;

    public int secondMagicNumber;

    public boolean upgradedSecondMagicNumber;

    public boolean isSecondMagicNumberModified;

    public int baseThirdMagicNumber;

    public int thirdMagicNumber;

    public boolean upgradedThirdMagicNumber;

    public boolean isThirdMagicNumberModified;

    public static String getID(Class<? extends DynamicCard> thisClazz) {
        return DynamicDungeon.makeID(thisClazz);
    }

    public static DynamicCard.InfoBuilder InfoBuilder(Class<? extends DynamicCard> thisClazz) {
        return new DynamicCard.InfoBuilder(thisClazz);
    }

    public static class InfoBuilder {

        private final Class<? extends DynamicCard> thisClazz;

        private final Set<CardTags> tags = new HashSet<>();

        private final Set<CardModifier> modifiers = new HashSet<>();

        private CardType type = CardType.STATUS;

        private CardRarity rarity = CardRarity.SPECIAL;

        private CardTarget target = CardTarget.NONE;

        private CardColor color = CardColor.COLORLESS;

        private int cost = -2;

        private int damage = -1;

        private int block = -1;

        private int magicNumber = -1;

        private int secondMagicNumber = -1;

        private int thirdMagicNumber = -1;

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
            final List<CardTags> tagsList = Arrays.asList(tags);
            this.tags.addAll(tagsList);
            return this;
        }

        public InfoBuilder modifiers(final CardModifier... modifiers) {
            final List<CardModifier> modifierList = Arrays.asList(modifiers);
            this.modifiers.addAll(modifierList);
            return this;
        }

        public InfoBuilder cost(final int cost) {
            this.cost = cost;
            return this;
        }

        public InfoBuilder damage(final int damage) {
            this.damage = damage;
            return this;
        }

        public InfoBuilder block(final int block) {
            this.block = block;
            return this;
        }

        public InfoBuilder magicNumber(final int magicNumber) {
            this.magicNumber = magicNumber;
            return this;
        }

        public InfoBuilder secondMagicNumber(final int secondMagicNumber) {
            this.secondMagicNumber = secondMagicNumber;
            return this;
        }

        public InfoBuilder thirdMagicNumber(final int thirdMagicNumber) {
            this.thirdMagicNumber = thirdMagicNumber;
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

        public final Set<CardModifier> modifiers;

        public final CardType type;

        public final CardRarity rarity;

        public final CardTarget target;

        private final CardColor color;

        public final int cost;

        public final int damage;

        public final int block;

        public final int magicNumber;

        public final int secondMagicNumber;

        public final int thirdMagicNumber;

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
            this.modifiers = builder.modifiers;
            this.type = builder.type;
            this.rarity = builder.rarity;
            this.target = builder.target;
            this.color = builder.color;
            this.cost = builder.cost;
            this.damage = builder.damage;
            this.block = builder.block;
            this.magicNumber = builder.magicNumber;
            this.secondMagicNumber = builder.secondMagicNumber;
            this.thirdMagicNumber = builder.thirdMagicNumber;
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
        // base values
        this.baseRawDescription = this.rawDescription;
        this.baseUpgradeRawDescription = info.cardStrings.UPGRADE_DESCRIPTION;
        this.baseExtendedDescriptions = info.cardStrings.EXTENDED_DESCRIPTION;
        this.baseDamage = info.damage;
        this.baseBlock = info.block;
        this.baseMagicNumber = info.magicNumber;
        this.baseSecondMagicNumber = info.secondMagicNumber;
        this.baseThirdMagicNumber = info.thirdMagicNumber;

        // tags
        this.tags.addAll(info.tags);

        // add card modifiers
        info.modifiers.forEach(a -> DynamicDungeon.addModifierToCard(this, a));

        // init values
        this.isMultiDamage = this.type == CardType.ATTACK && this.target == CardTarget.NONE;

        // description
        resetAttributes();
        initializeDescription();
    }

    @Override
    public void initializeDescription() {
        if (this.baseRawDescription != null) {
            initBaseRawDescription();
        }
        super.initializeDescription();
    }

    private void initBaseRawDescription() {
        this.rawDescription = this.upgraded && StringUtils.isNotBlank(this.baseUpgradeRawDescription) ?
                this.baseUpgradeRawDescription :
                this.baseRawDescription;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (this.upgradedSecondMagicNumber) {
            this.secondMagicNumber = this.baseSecondMagicNumber;
            this.isSecondMagicNumberModified = true;
        }
        if (this.upgradedThirdMagicNumber) {
            this.thirdMagicNumber = this.baseThirdMagicNumber;
            this.isThirdMagicNumberModified = true;
        }
    }

    public void upgradeSecondMagicNumber(int amount) {
        this.baseSecondMagicNumber += amount;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.upgradedSecondMagicNumber = true;
    }

    public void upgradeThirdMagicNumber(int amount) {
        this.baseThirdMagicNumber += amount;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.upgradedThirdMagicNumber = true;
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.isSecondMagicNumberModified = false;
        this.thirdMagicNumber = this.baseThirdMagicNumber;
        this.isThirdMagicNumberModified = false;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeValues();
            initializeDescription();
        }
    }

    protected void upgradeValues() {}

    public void triggerOnFlashback() {}

    public void triggerOnFuturity() {}

    public void triggerOnElementless() {}

    public void triggerOnPresenceOfMind() {}

    public void triggerOnElementCost(final ElementAmount elementAmountSpend) {}

    public void triggerOnMakeFire() {}

    public void triggerOnMakeIce() {}

    public void triggerOnMakeArcane() {}

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        DynamicCard card = (DynamicCard) super.makeStatEquivalentCopy();

        card.baseRawDescription = this.baseRawDescription;
        card.baseUpgradeRawDescription = this.baseUpgradeRawDescription;
        card.baseSecondMagicNumber = this.baseSecondMagicNumber;
        card.baseThirdMagicNumber = this.baseThirdMagicNumber;
        return card;
    }
}

package theSorcerer.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.DynamicPower;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class DynamicPotion extends CustomPotion {

    protected String[] descriptions;

    public DynamicPotion(
            Class<? extends DynamicPotion> thisClazz,
            PotionRarity rarity,
            PotionSize potionSize,
            PotionColor potionColor
    ) {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(
                thisClazz.getSimpleName(),
                DynamicPotion.getID(thisClazz),
                rarity,
                potionSize,
                potionColor
        );
    }

    public static String getID(final Class<? extends DynamicPotion> thisClazz) {
        return DynamicDungeon.makeID(thisClazz);
    }

    @Override
    public void initializeData() {
        // Potency is the damage/magic number equivalent of potions.
        this.potency = getPotency();

        PotionStrings potionString = CardCrawlGame.languagePack.getPotionString(this.ID);
        this.name = potionString.NAME;
        this.descriptions = potionString.DESCRIPTIONS;

        // Initialize the Description
        updateDescription();

        // Initialize the on-hover name + description
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    protected final void addTip(final String... keyword) {
        this.tips.addAll(
                Arrays.stream(keyword).map(DynamicDungeon::getPowerTip).collect(Collectors.toList())
        );
    }

    @SafeVarargs
    protected final void addTip(final Class<? extends DynamicPower>... powerClass) {
        this.tips.addAll(
                Arrays.stream(powerClass).map(DynamicDungeon::getPowerTip).collect(Collectors.toList())
        );
    }

    protected abstract void updateDescription();
}

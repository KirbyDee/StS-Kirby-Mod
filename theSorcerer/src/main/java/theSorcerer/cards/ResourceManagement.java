package theSorcerer.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.ElementAffinityCardCostModifier;

public class ResourceManagement extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int ENERGY_GAIN = 1;
    private static final int UPGRADE_ENERGY_GAIN = 1;
    // --- VALUES END ---

    public ResourceManagement() {
        super(
                DynamicCard.InfoBuilder(ResourceManagement.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .magicNumber(ENERGY_GAIN)
        );
        CardModifierManager.addModifier(this, new ElementAffinityCardCostModifier());
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        DynamicDungeon.gainEnergy(this.magicNumber);
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_ENERGY_GAIN);
    }
}

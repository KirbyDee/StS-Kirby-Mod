package theSorcerer.cards.arcane;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;

public class Meditation extends SorcererArcaneCard {

    // --- VALUES START ---
    private static final int COST = 3;
    private static final int GAIN_ENERGY = 3;
    private static final int UPGRADE_GAIN_ENERGY = 1;
    // --- VALUES END ---

    public Meditation() {
        super(
                DynamicCard.InfoBuilder(Meditation.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .magicNumber(GAIN_ENERGY)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        DynamicDungeon.gainEnergy(this.magicNumber);
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_GAIN_ENERGY);
    }
}

package theSorcerer.cards.arcane;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;

public class Harmony extends SorcererArcaneCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    // --- VALUES END ---

    public Harmony() {
        super(
                DynamicCard.InfoBuilder(Harmony.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.COMMON)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {}

    @Override
    protected void upgradeValues() {
        upgradeBaseCost(UPGRADE_COST);
    }
}

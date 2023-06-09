package theSorcerer.cards.arcane;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.ArcaneCometsAction;
import theSorcerer.cards.DynamicCard;

public class ArcaneComets extends SorcererArcaneCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int DAMAGE = 2;
    private static final int DAMAGE_TIMES = 4;
    private static final int UPGRADE_DAMAGE_TIMES = 1;
    // --- VALUES END ---

    public ArcaneComets() {
        super(
                DynamicCard.InfoBuilder(ArcaneComets.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.UNCOMMON)
                        .damage(DAMAGE)
                        .magicNumber(DAMAGE_TIMES)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        for (int i = 0; i < this.magicNumber; ++i) {
            comet(monster);
        }
    }

    private void comet(final AbstractMonster monster) {
        addToBot(
                new ArcaneCometsAction(
                        this
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_DAMAGE_TIMES);
    }
}

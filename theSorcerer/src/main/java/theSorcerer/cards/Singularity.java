package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.SingularityAction;
import theSorcerer.cards.arcane.SorcererArcaneCard;
import theSorcerer.patches.characters.AbstractPlayerPatch;

public class Singularity extends SorcererArcaneCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int ARCANE_CARDS_PLAYED = 0;
    // --- VALUES END ---

    public Singularity() {
        super(
                DynamicCard.InfoBuilder(Singularity.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.RARE)
                        .damage(DAMAGE)
                        .magicNumber(ARCANE_CARDS_PLAYED)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        for (int i = 0; i < AbstractPlayerPatch.arcaneCardsPlayedPerCombat.get(AbstractDungeon.player); i++) {
            addToBot(
                    new SingularityAction(this)
            );
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = AbstractPlayerPatch.arcaneCardsPlayedPerCombat.get(AbstractDungeon.player);
        initializeDescription();
    }

    @Override
    public void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}

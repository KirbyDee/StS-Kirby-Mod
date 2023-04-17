package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.actions.ExplosionAction;
import theSorcerer.cards.DynamicCard;

public class Explosion extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 1;
    // --- VALUES END ---

    public Explosion() {
        super(
                DynamicCard.InfoBuilder(Explosion.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.ENEMY)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ExplosionAction(
                        player,
                        monster
                )
        );
    }

    @Override
    protected void upgradeValues() {
        DynamicDungeon.makeCardFlashback(this);
    }
}

package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.actions.ImplosionAction;
import theSorcerer.cards.DynamicCard;

public class Implosion extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 1;
    // --- VALUES END ---

    public Implosion() {
        super(
                DynamicCard.InfoBuilder(Implosion.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.ENEMY)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ImplosionAction(
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

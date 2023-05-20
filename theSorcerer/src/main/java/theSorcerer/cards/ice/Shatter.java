package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.actions.ShatterAction;
import theSorcerer.cards.DynamicCard;

public class Shatter extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 1;
    // --- VALUES END ---

    public Shatter() {
        super(
                DynamicCard.InfoBuilder(Shatter.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.ENEMY)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ShatterAction(
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

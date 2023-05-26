package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;

public class ColdFeet extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 1;
    // --- VALUES END ---

    public ColdFeet() {
        super(
                DynamicCard.InfoBuilder(ColdFeet.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {}

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        if (AbstractDungeon.player.discardPile.group.contains(this)) {
            DynamicDungeon.applyChilled(this.costForTurn);
        }
    }

    @Override
    protected void upgradeValues() {
        DynamicDungeon.makeCardEntomb(this);
    }
}

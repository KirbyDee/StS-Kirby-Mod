package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;

public class WarmHands extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 1;
    // --- VALUES END ---

    public WarmHands() {
        super(
                DynamicCard.InfoBuilder(WarmHands.class)
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
            DynamicDungeon.applyHeated(this.costForTurn);
        }
    }

    @Override
    protected void upgradeValues() {
        DynamicDungeon.makeCardEntomb(this);
    }
}

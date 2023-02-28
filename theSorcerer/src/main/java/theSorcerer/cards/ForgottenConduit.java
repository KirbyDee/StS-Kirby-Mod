package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;

public class UnseenHelper extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = -2;
    private static final int ENERGY_GAIN = 1;
    // --- VALUES END ---

    public UnseenHelper() {
        super(
                DynamicCard.InfoBuilder(UnseenHelper.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.RARE)
                        .target(CardTarget.NONE)
                        .magicNumber(ENERGY_GAIN)
                        .abilities(CardAbility.UNPLAYABLE)
        );
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}

    @Override
    public void atTurnStart() {
        if (AbstractDungeon.player.discardPile.group.contains(this)) {
            addToTop(new GainEnergyAction(this.magicNumber));
        }
    }

    @Override
    protected void upgradeValues() {
        AbstractCardPatch.abilities.get(this).add(CardAbility.ENTOMB);
    }
}

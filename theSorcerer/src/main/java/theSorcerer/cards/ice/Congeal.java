package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;

public class Congeal extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int BLOCK_MULTIPLIER = 4;
    // --- VALUES END ---

    public Congeal() {
        super(
                DynamicCard.InfoBuilder(Congeal.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.SELF)
                        .magicNumber(BLOCK_MULTIPLIER)
                        .secondMagicNumber(0)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new GainBlockAction(
                        player,
                        player,
                        this.secondMagicNumber
                )
        );
    }

    public void applyPowers() {
        super.applyPowers();
        this.baseSecondMagicNumber = DynamicDungeon.getFireAffinityAmount() * this.magicNumber;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        initializeDescription();
    }

    @Override
    protected void upgradeValues() {
        AbstractCardPatch.abilities.get(this).add(CardAbility.FLASHBACK);
    }
}
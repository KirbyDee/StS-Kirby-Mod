package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.fire.Melt;

public class Congeal extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;
    private static final int DAMAGE_MULTIPLIER = 4;
    // --- VALUES END ---

    public Congeal() {
        super(
                DynamicCard.InfoBuilder(Congeal.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ENEMY)
                        .magicNumber(DAMAGE_MULTIPLIER)
                        .secondMagicNumber(0)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new DamageAction(
                        monster,
                        new DamageInfo(player, this.secondMagicNumber, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.NONE
                )
        );
    }

    public void applyPowers() {
        super.applyPowers();
        this.baseSecondMagicNumber = DynamicDungeon.getHeatedAmount() * this.magicNumber;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        initializeDescription();
    }

    @Override
    protected void upgradeValues() {
        upgradeBaseCost(UPGRADE_COST);
    }
}

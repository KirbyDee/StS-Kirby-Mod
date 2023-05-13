package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;

public class Melt extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int DAMAGE_MULTIPLIER = 3;
    // --- VALUES END ---

    public Melt() {
        super(
                DynamicCard.InfoBuilder(Melt.class)
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
                        AbstractGameAction.AttackEffect.FIRE
                )
        );
    }

    public void applyPowers() {
        super.applyPowers();
        this.baseSecondMagicNumber = DynamicDungeon.getChilledAmount() * this.magicNumber;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        initializeDescription();
    }

    @Override
    protected void upgradeValues() {
        DynamicDungeon.makeCardFlashback(this);
    }
}

package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;

public class FrozenTomb extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int PLUS_DAMAGE = 4;
    private static final int UPGRADE_PLUS_DAMAGE = 3;
    // --- VALUES END ---

    public FrozenTomb() {
        super(
                DynamicCard.InfoBuilder(FrozenTomb.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ENEMY)
                        .magicNumber(PLUS_DAMAGE)
                        .secondMagicNumber(0)
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(
                new DamageAction(
                        m,
                        new DamageInfo(p, this.secondMagicNumber, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
                )
        );
    }

    public void applyPowers() {
        super.applyPowers();
        this.baseSecondMagicNumber = AbstractDungeon.player.discardPile.size() + this.baseMagicNumber;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        initializeDescription();
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_PLUS_DAMAGE);
    }
}

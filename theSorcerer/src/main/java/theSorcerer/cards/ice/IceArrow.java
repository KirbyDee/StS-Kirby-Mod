package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.special.IceCharm;

public class IceArrow extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DAMAGE = 3;
    private static final int TOKEN_AMOUNT = 1;
    // --- VALUES END ---

    public IceArrow() {
        super(
                DynamicCard.InfoBuilder(IceArrow.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
                        .magicNumber(TOKEN_AMOUNT)
        );

        this.cardsToPreview = new IceCharm();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new DamageAction(
                        monster,
                        new DamageInfo(player, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT
                )
        );

        AbstractCard card = this.cardsToPreview.makeStatEquivalentCopy();
        if (this.upgraded) {
            card.upgrade();
        }
        addToBot(
                new MakeTempCardInDrawPileAction(
                        card,
                        this.magicNumber,
                        true,
                        true
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeBlock(UPGRADE_PLUS_DAMAGE);
        this.cardsToPreview.upgrade();
    }
}

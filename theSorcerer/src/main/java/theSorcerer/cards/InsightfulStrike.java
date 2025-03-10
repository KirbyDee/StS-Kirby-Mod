package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.actions.InsightfulStrikeAction;

public class InsightfulStrike extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int DRAW_CARDS = 1;
    private static final int DRAW_ADDITIONAL_CARDS = 1;
    // --- VALUES END ---

    public InsightfulStrike() {
        super(
                DynamicCard.InfoBuilder(InsightfulStrike.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .tags(CardTags.STRIKE)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
                        .magicNumber(DRAW_CARDS)
                        .secondMagicNumber(DRAW_ADDITIONAL_CARDS)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // damage
        addToBot(
                new DamageAction(
                        monster,
                        new DamageInfo(player, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
                )
        );
        DynamicDungeon.drawCard(this.magicNumber, new InsightfulStrikeAction(this.secondMagicNumber));
        addToBot(new WaitAction(0.5F));
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}

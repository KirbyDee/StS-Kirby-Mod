package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import theSorcerer.modifiers.CardModifier;

public class RecurringStrike extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int STRENGTH_AMOUNT = 1;
    private static final int UPGRADE_STRENGTH_AMOUNT = 1;
    // --- VALUES END ---

    public RecurringStrike() {
        super(
                DynamicCard.InfoBuilder(RecurringStrike.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
                        .tags(CardTags.STRIKE)
                        .modifiers(CardModifier.FLASHBACK)
                        .magicNumber(STRENGTH_AMOUNT)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new DamageAction(
                        monster,
                        new DamageInfo(player, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL
                )
        );
    }

    @Override
    public void triggerOnFlashback() {
        superFlash();
        addToBot(
                new ApplyPowerAction(
                        AbstractDungeon.player,
                        AbstractDungeon.player,
                        new StrengthPower(AbstractDungeon.player, this.magicNumber),
                        this.magicNumber
                )
        );
    }

    @Override
    public void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_STRENGTH_AMOUNT);
    }
}

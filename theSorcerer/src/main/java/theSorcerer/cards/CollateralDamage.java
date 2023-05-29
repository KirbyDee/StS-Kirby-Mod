package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;
import theSorcerer.powers.buff.CollateralDamagePower;

public class CollateralDamage extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 3;
    private static final int PERCENT_DAMAGE = 10;
    private static final int UPGRADE_PERCENT_DAMAGE = 5;
    // --- VALUES END ---

    public CollateralDamage() {
        super(
                DynamicCard.InfoBuilder(CollateralDamage.class)
                        .cost(COST)
                        .type(CardType.POWER)
                        .rarity(CardRarity.RARE)
                        .target(CardTarget.SELF)
                        .magicNumber(PERCENT_DAMAGE)
                        .modifiers(CardModifier.ETHEREAL)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new ApplyPowerAction(
                        player,
                        player,
                        new CollateralDamagePower(
                                player,
                                this.magicNumber
                        )
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_PERCENT_DAMAGE);
        DynamicDungeon.removeModifierFromCard(this, CardModifier.ETHEREAL);
    }
}

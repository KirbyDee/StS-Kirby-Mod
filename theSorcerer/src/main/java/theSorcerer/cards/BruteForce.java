package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;

public class BruteForce extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 3;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int ELEMENTLESS_TIMES = 0;
    // --- VALUES END ---

    public BruteForce() {
        super(
                DynamicCard.InfoBuilder(BruteForce.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.RARE)
                        .damage(DAMAGE)
                        .modifiers(CardModifier.EXHAUST)
                        .magicNumber(ELEMENTLESS_TIMES)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        for (int i = 0; i < this.magicNumber; ++i) {
            addToBot(
                    new AttackDamageRandomEnemyAction(
                            this,
                            AbstractGameAction.AttackEffect.BLUNT_LIGHT
                    )
            );
        }
    }

    @Override
    public void triggerOnElementless() {
        this.baseMagicNumber++;
        this.magicNumber = this.baseMagicNumber;
        initializeDescription();
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
        DynamicDungeon.removeModifierFromCard(this, CardModifier.EXHAUST);
    }
}

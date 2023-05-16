package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.patches.cards.CardAbility;

public class ImprudentPunches extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 3;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int ELEMENTLESS_TIMES = 0;
    // --- VALUES END ---

    public ImprudentPunches() {
        super(
                DynamicCard.InfoBuilder(ImprudentPunches.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.ALL_ENEMY)
                        .damage(DAMAGE)
                        .abilities(CardAbility.EXHAUST)
                        .magicNumber(ELEMENTLESS_TIMES)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        for (int i = 0; i < this.magicNumber; ++i) {
            addToBot(new AttackDamageRandomEnemyAction(this));
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
        this.exhaust = false;
    }
}

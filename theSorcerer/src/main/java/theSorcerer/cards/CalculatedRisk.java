package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;

public class CalculatedRisk extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 3;
    private static final int DAMAGE = 15;
    private static final int UPGRADE_DAMAGE = 5;
    private static final int BLOCK = 15;
    private static final int UPGRADE_BLOCK = 5;
    // --- VALUES END ---

    public CalculatedRisk() {
        super(
                DynamicCard.InfoBuilder(CalculatedRisk.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.ALL_ENEMY)
                        .damage(DAMAGE)
                        .block(BLOCK)
                        .modifiers(CardModifier.ELEMENTCOST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new DamageAllEnemiesAction(
                        player,
                        this.multiDamage,
                        this.damageType,
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                        true
                )
        );
        addToBot(
                new GainBlockAction(
                        player,
                        player,
                        this.block
                )
        );
        DynamicDungeon.applyElementless();
    }


    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeBlock(UPGRADE_BLOCK);
    }
}

package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;

public class PillarOfFlame extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int DAMAGE_PRIMARY = 10;
    private static final int UPGRADE_DAMAGE_PRIMARY = 2;
    private static final int DAMAGE_SECONDARY = 5;
    private static final int UPGRADE_DAMAGE_SECONDARY = 1;
    // --- VALUES END ---

    public PillarOfFlame() {
        super(
                DynamicCard.InfoBuilder(PillarOfFlame.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.NONE)
                        .damage(DAMAGE_SECONDARY)
                        .magicNumber(DAMAGE_PRIMARY)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        int numberOfEnemies = AbstractDungeon.getCurrRoom().monsters.monsters.size();
        int randomEnemyIndex = AbstractDungeon.miscRng.random.nextInt(numberOfEnemies);
        this.multiDamage[randomEnemyIndex] = this.magicNumber;

        // damage all enemies
        addToBot(
                new DamageAllEnemiesAction(
                        player,
                        this.multiDamage,
                        this.damageTypeForTurn,
                        AbstractGameAction.AttackEffect.FIRE
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE_SECONDARY);
        upgradeMagicNumber(UPGRADE_DAMAGE_PRIMARY);
    }
}

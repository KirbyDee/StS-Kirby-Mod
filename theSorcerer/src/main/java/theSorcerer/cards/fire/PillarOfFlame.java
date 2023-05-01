package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import theSorcerer.actions.DamageMultipleEnemiesAction;
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
                        .target(CardTarget.ALL_ENEMY)
                        .damage(DAMAGE_SECONDARY)
                        .magicNumber(DAMAGE_PRIMARY)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        MonsterGroup monsterGroup = AbstractDungeon.getCurrRoom().monsters;
        AbstractMonster randomMonster = monsterGroup.getRandomMonster(true);

        addToBot(
                new DamageAction(
                        randomMonster,
                        new DamageInfo(player, this.magicNumber, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE
                )
        );

        this.multiDamage[monsterGroup.monsters.indexOf(randomMonster)] = -1;
        addToBot(
                new DamageMultipleEnemiesAction(
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

package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.powers.debuff.IgnitePower;

public class Ignite extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int LOSE_HEALTH = 6;
    private static final int LOSE_HEALTH_TURNS = 1;
    private static final int UPGRADE_LOSE_HEALTH_TURNS = 1;
    // --- VALUES END ---

    public Ignite() {
        super(
                DynamicCard.InfoBuilder(Ignite.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.RARE)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
                        .magicNumber(LOSE_HEALTH)
                        .secondMagicNumber(LOSE_HEALTH_TURNS)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // damage
        addToBot(
                new DamageAction(
                        monster,
                        new DamageInfo(player, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE
                )
        );

        // add ignite debuff
        addToBot(
                new ApplyPowerAction(
                        monster,
                        player,
                        new IgnitePower(
                                monster,
                                player,
                                this.magicNumber,
                                this.secondMagicNumber,
                                true
                        ),
                        this.secondMagicNumber
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_PLUS_DMG);
        upgradeSecondMagicNumber(UPGRADE_LOSE_HEALTH_TURNS);
    }
}

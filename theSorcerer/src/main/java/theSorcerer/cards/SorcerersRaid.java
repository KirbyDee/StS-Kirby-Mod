package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theSorcerer.DynamicDungeon;

public class SorcerersRaid extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_DAMAGE = 11;
    private static final int WEAK_VULNERABLE = 1;
    private static final int UPGRADE_WEAK_VULNERABLE = 1;
    // --- VALUES END ---

    public SorcerersRaid() {
        super(
                DynamicCard.InfoBuilder(SorcerersRaid.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .damage(DAMAGE)
                        .magicNumber(WEAK_VULNERABLE)
                        .target(CardTarget.ALL_ENEMY)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        AbstractDungeon.getCurrRoom().monsters.monsters
                .forEach(m -> applyWeakAndVulnerable(player, m));

        DynamicDungeon.applyElementless();
    }

    private void applyWeakAndVulnerable(AbstractPlayer player, AbstractMonster monster) {
        applyDebuff(player, monster, new WeakPower(monster, this.magicNumber, false));
        applyDebuff(player, monster, new VulnerablePower(monster, this.magicNumber, false));
    }

    private void applyDebuff(final AbstractPlayer player, final AbstractMonster monster, final AbstractPower power) {
        addToBot(
                new ApplyPowerAction(monster, player, power, this.magicNumber, true, AbstractGameAction.AttackEffect.NONE)
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_WEAK_VULNERABLE);
    }
}

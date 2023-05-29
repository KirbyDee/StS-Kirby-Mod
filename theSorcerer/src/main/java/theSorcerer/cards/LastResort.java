package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.debuff.ElementlessPower;

public class LastResort extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 0;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int VULNERABLE = 1;
    private static final int UPGRADE_VULNERABLE = 1;
    private static final int VULNERABLE_ELEMENTLESS = 2;
    private static final int UPGRADE_VULNERABLE_ELEMENTLESS = 2;
    // --- VALUES END ---

    public LastResort() {
        super(
                DynamicCard.InfoBuilder(LastResort.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
                        .magicNumber(VULNERABLE)
                        .secondMagicNumber(VULNERABLE_ELEMENTLESS)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        int vulnerable = this.magicNumber;
        if (DynamicDungeon.hasElementless()) {
            vulnerable = this.secondMagicNumber;
        }

        // damage
        addToBot(
                new DamageAction(
                        monster,
                        new DamageInfo(
                                monster,
                                this.damage,
                                this.damageTypeForTurn
                        ),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT
                )
        );

        // vulnerable
        addToBot(
                new ApplyPowerAction(
                        monster,
                        player,
                        new VulnerablePower(
                                monster,
                                vulnerable,
                                false
                        ),
                        vulnerable,
                        true,
                        AbstractGameAction.AttackEffect.NONE
                )
        );
    }

    public void triggerOnGlowCheck() {
        if (DynamicDungeon.hasElementless()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
        else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_VULNERABLE);
        upgradeSecondMagicNumber(UPGRADE_VULNERABLE_ELEMENTLESS);
    }
}

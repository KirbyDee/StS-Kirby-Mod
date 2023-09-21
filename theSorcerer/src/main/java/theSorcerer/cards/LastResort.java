package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theSorcerer.DynamicDungeon;

public class LastResort extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 0;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_DAMAGE = 2;
    private static final int VULNERABLE = 1;
    private static final int UPGRADE_VULNERABLE = 1;
    private static final int VULNERABLE_ELEMENTLESS = 2;
    private static final int UPGRADE_VULNERABLE_ELEMENTLESS = 2;
    // --- VALUES END ---
    private int vulnerable;

    public LastResort() {
        super(
                DynamicCard.InfoBuilder(LastResort.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
                        .magicNumber(VULNERABLE)
                        .tags(SorcererCardTags.ELEMENTLESS)
                        .secondMagicNumber(VULNERABLE_ELEMENTLESS)
        );
        this.vulnerable = this.magicNumber;
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // damage
        addToBot(
                new DamageAction(
                        monster,
                        new DamageInfo(
                                player,
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
                                this.vulnerable,
                                false
                        ),
                        this.vulnerable,
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
    public void triggerIfElementless() {
        triggerOnElementless();
    }

    @Override
    public void triggerOnElementless() {
        this.vulnerable = this.secondMagicNumber;
    }

    @Override
    public void triggerOnNotElementlessAnymore() {
        this.vulnerable = this.magicNumber;
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_VULNERABLE);
        upgradeSecondMagicNumber(UPGRADE_VULNERABLE_ELEMENTLESS);
    }
}

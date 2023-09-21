package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;

public class KnucklePunch extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_DAMAGE = 4;
    private static final int BASE_TIMES = 1;
    private static final int ELEMENTLESS_FACTOR = 2;
    // --- VALUES END ---
    private int times;

    public KnucklePunch() {
        super(
                DynamicCard.InfoBuilder(KnucklePunch.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
                        .tags(SorcererCardTags.ELEMENTLESS)
                        .magicNumber(BASE_TIMES)
                        .secondMagicNumber(ELEMENTLESS_FACTOR)
        );
        this.times = this.magicNumber;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for (int i = 0; i < this.times; i++) {
            addToBot(
                    new DamageAction(
                            abstractMonster,
                            new DamageInfo(abstractPlayer, this.damage, this.damageTypeForTurn),
                            AbstractGameAction.AttackEffect.BLUNT_HEAVY
                    )
            );
        }
    }

    @Override
    public void triggerIfElementless() {
        triggerOnElementless();
    }

    @Override
    public void triggerOnElementless() {
        this.times = this.secondMagicNumber;
    }

    @Override
    public void triggerOnNotElementlessAnymore() {
        this.times = this.magicNumber;
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
    }
}

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
    private static final int ELEMENTLESS_FACTOR = 2;
    // --- VALUES END ---

    public KnucklePunch() {
        super(
                DynamicCard.InfoBuilder(KnucklePunch.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
                        .tags(SorcererCardTags.ELEMENTLESS)
                        .magicNumber(ELEMENTLESS_FACTOR)
        );
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int times = 1;
        if (DynamicDungeon.hasElementless()) {
            times = ELEMENTLESS_FACTOR;
        }

        for (int i = 0; i < times; i++) {
            addToBot(
                    new DamageAction(
                            abstractMonster,
                            new DamageInfo(abstractMonster, this.damage, this.damageTypeForTurn),
                            AbstractGameAction.AttackEffect.BLUNT_HEAVY
                    )
            );
        }
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

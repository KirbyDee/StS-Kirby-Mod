package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.debuff.ElementlessPower;

public class LostKnowledge extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_DAMAGE = 3;
    private static final int ELEMENTLESS_DAMAGE = 16;
    private static final int UPGRADE_ELEMENTLESS_DAMAGE = 20;
    // --- VALUES END ---

    public LostKnowledge() {
        super(
                DynamicCard.InfoBuilder(LostKnowledge.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
                        .magicNumber(ELEMENTLESS_DAMAGE)
        );
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int d = this.damage;
        AbstractGameAction.AttackEffect effect = AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
        if(DynamicDungeon.hasElementless()) {
            d = this.magicNumber;
            effect = AbstractGameAction.AttackEffect.SLASH_HEAVY;
        }

        addToBot(
                new DamageAction(
                        abstractMonster,
                        new DamageInfo(abstractMonster, d, this.damageTypeForTurn),
                        effect
                )
        );
    }

    public void triggerOnGlowCheck() {
        if (AbstractDungeon.player.hasPower(ElementlessPower.POWER_ID)) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
        else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeMagicNumber(UPGRADE_ELEMENTLESS_DAMAGE);
    }
}

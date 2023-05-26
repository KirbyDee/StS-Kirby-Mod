package theSorcerer.cards.fire;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;

public class Combustion extends SorcererFireCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 3;
    // --- VALUES END ---

    public Combustion() {
        super(
                DynamicCard.InfoBuilder(Combustion.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
        );
    }

    @Override
    public boolean canUse(AbstractPlayer player, AbstractMonster monster) {
        return super.canUse(player, monster) && DynamicDungeon.isHeated();
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new DamageAction(
                        monster,
                        new DamageInfo(player, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE
                )
        );
        DynamicDungeon.drawCard(this.magicNumber);
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_PLUS_DMG);
    }
}

package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Brawl extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 0;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_DAMAGE = 3;
    // --- VALUES END ---

    public Brawl() {
        super(
                DynamicCard.InfoBuilder(Brawl.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new DamageAction(
                        monster,
                        new DamageInfo(player, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY
                )
        );
    }

    @Override
    public void triggerOnElementless() {
        if (AbstractDungeon.player.discardPile.group.contains(this)) {
            addToBot(new DiscardToHandAction(this));
            superFlash();
        }
    }


    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}

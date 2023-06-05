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
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int ENERGY_GAIN = 1;
    public boolean glowCheckTriggered = false;
    // --- VALUES END ---

    public Combustion() {
        super(
                DynamicCard.InfoBuilder(Combustion.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ENEMY)
                        .magicNumber(ENERGY_GAIN)
                        .damage(DAMAGE)
        );
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

        if (this.glowCheckTriggered) {
            DynamicDungeon.gainEnergy(this.magicNumber);
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowCheckTriggered = DynamicDungeon.isLastCardPlayed(DynamicDungeon::isFireCard);
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_PLUS_DMG);
    }
}

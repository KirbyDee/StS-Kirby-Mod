package theSorcerer.cards;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.actions.DecayAction;
import theSorcerer.patches.cards.CardAbility;

public class Decay extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 2;
    private static final int DAMAGE = 12;
    private static final int UPGRADE_DAMAGE = 4;
    // --- VALUES END ---

    public Decay() {
        super(
                DynamicCard.InfoBuilder(Decay.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.RARE)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
                        .abilities(CardAbility.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new DecayAction(
                        monster,
                        new DamageInfo(
                                player,
                                this.damage,
                                this.damageTypeForTurn
                        )
                )
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
    }
}

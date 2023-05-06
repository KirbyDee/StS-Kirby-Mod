package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;

public class UndecidedChoice extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DAMAGE = 3;
    private static final int BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    // --- VALUES END ---

    public UndecidedChoice() {
        super(
                DynamicCard.InfoBuilder(UndecidedChoice.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
                        .block(BLOCK)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new DamageAction(
                        monster,
                        new DamageInfo(player, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL
                )
        );
        addToBot(
                new GainBlockAction(
                        player,
                        player,
                        this.block
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
    public void triggerOnElementless() {
        setCostForTurn(0);
    }

    @Override
    protected void upgradeValues() {
        upgradeDamage(UPGRADE_PLUS_DAMAGE);
        upgradeBlock(UPGRADE_PLUS_BLOCK);
    }
}

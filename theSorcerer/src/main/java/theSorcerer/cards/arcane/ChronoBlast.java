package theSorcerer.cards.arcane;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import theSorcerer.cards.DynamicCard;
import theSorcerer.modifiers.CardModifier;

import java.util.List;

public class ChronoBlast extends SorcererArcaneCard {

    // --- VALUES START ---
    private static final int COST = 6;
    private static final int UPGRADE_COST = 5;
    private static final int DAMAGE = 20;
    private static final int UPGRADE_DAMAGE = 6;
    // --- VALUES END ---

    public ChronoBlast() {
        super(
                DynamicCard.InfoBuilder(ChronoBlast.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.RARE)
                        .target(CardTarget.ALL_ENEMY)
                        .damage(DAMAGE)
                        .modifiers(CardModifier.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        List<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
        // damage
        if (monsters != null && !monsters.isEmpty()) {
            AbstractMonster monsterMostLeft = monsters.get(0);
            AbstractMonster monsterMostRight = monsters.get(monsters.size() - 1);
            final float cX = (monsterMostRight.hb.cX + monsterMostLeft.hb.cX) / 2;
            final float cY = (monsterMostRight.hb.cY + monsterMostLeft.hb.cY) / 2;
            addToBot(
                    new VFXAction(
                            new WeightyImpactEffect(
                                    cX,
                                    cY
                            )
                    )
            );
            addToBot(new WaitAction(0.8F));
            addToBot(
                    new DamageAllEnemiesAction(
                            player,
                            this.multiDamage,
                            this.damageType,
                            AbstractGameAction.AttackEffect.NONE,
                            true
                    )
            );
            addToBot(new WaitAction(0.25F));
        }

        // extra turn
        addToBot(
                new VFXAction(
                        new WhirlwindEffect(
                                new Color(1.0F, 1.0F, 1.0F, 1.0F),
                                true
                        )
                )
        );
        addToBot(new SkipEnemiesTurnAction());
        addToBot(new PressEndTurnButtonAction());
    }

    @Override
    public void upgradeValues() {
        upgradeDamage(UPGRADE_DAMAGE);
        upgradeBaseCost(UPGRADE_COST);
    }
}

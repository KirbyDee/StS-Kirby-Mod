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
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;
import theSorcerer.patches.cards.CardAbility;

import java.util.List;

public class ChronoBlast extends SorcererArcaneCard {

    // --- VALUES START ---
    private static final int COST = 6;
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
                        .abilities(CardAbility.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        List<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
        // damage
        if (monsters != null && !monsters.isEmpty()) {
            // TODOO: middle of hb.cX and hb.cY for first and last monster
            AbstractMonster middleMonster = monsters.get(monsters.size() / 2);
            addToBot(
                    new VFXAction(
                            new WeightyImpactEffect(
                                    middleMonster.hb.cX,
                                    middleMonster.hb.cY
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
        DynamicDungeon.makeCardFuturity(this);
    }
}

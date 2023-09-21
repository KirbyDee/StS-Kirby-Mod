package theSorcerer.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.debuff.AblazePower;
import theSorcerer.powers.debuff.FrozenPower;

public class BagOfIce extends DynamicRelic {

    private static final int DEBUFF_AMOUNT = 2;

    public BagOfIce() {
        super(
                BagOfIce.class,
                RelicTier.COMMON,
                LandingSound.MAGICAL
        );
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        addTip(
                AblazePower.class,
                FrozenPower.class
        );
    }

    @Override
    public void atBattleStart() {
        DynamicDungeon.triggerRelic(this);
        AbstractDungeon.getCurrRoom().monsters.monsters
                .forEach(this::applyElementDebuffs);
    }

    private void applyElementDebuffs(final AbstractMonster monster) {
        addToBot(
                new RelicAboveCreatureAction(monster,
                        this
                )
        );
        addToBot(
                new ApplyPowerAction(
                        monster,
                        AbstractDungeon.player,
                        new AblazePower(
                                monster,
                                DEBUFF_AMOUNT,
                                false
                        ),
                        DEBUFF_AMOUNT
                )
        );
        addToBot(
                new ApplyPowerAction(
                        monster,
                        AbstractDungeon.player,
                        new FrozenPower(
                                monster,
                                DEBUFF_AMOUNT,
                                false
                        ),
                        DEBUFF_AMOUNT
                )
        );
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DEBUFF_AMOUNT + DESCRIPTIONS[1];
    }
}

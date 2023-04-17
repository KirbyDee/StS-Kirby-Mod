package theSorcerer.potions;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.DynamicPower;
import theSorcerer.powers.buff.ElementPower;

public abstract class ElementPotion extends DynamicPotion {

    private final Class<? extends ElementPower<?>> oppositeElementPowerClazz;

    public ElementPotion(
            Class<? extends DynamicPotion> thisClazz,
            Class<? extends ElementPower<?>> oppositeElementPowerClazz,
            PotionColor potionColor
    ) {
        super(thisClazz, PotionRarity.COMMON, PotionSize.BOTTLE, potionColor);
        this.oppositeElementPowerClazz = oppositeElementPowerClazz;
        this.isThrown = false;
    }

    protected void useElementPotion(){
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            DynamicDungeon.runIfNotElementless(this::tryToApplyElementPower);
        }
    }

    private void tryToApplyElementPower() {
        if (AbstractDungeon.player.hasPower(DynamicPower.getID(oppositeElementPowerClazz))) {
            DynamicDungeon.applyElementless();
        }
        else {
            applyElementPower(this.potency);
        }
    }

    protected abstract void applyElementPower(int amount);

    @Override
    protected void updateDescription() {
        this.description = this.descriptions[0] + this.potency + this.descriptions[1];
    }
}

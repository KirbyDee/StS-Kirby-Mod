package theSorcerer.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.buff.HeatedPower;

public class HeatedPotion extends ElementPotion {

    private static final int POTENCY = 2;

    public HeatedPotion() {
        super(HeatedPotion.class, ChilledPower.class, PotionColor.FIRE);
    }


    @Override
    public void initializeData() {
        super.initializeData();
        addTip(HeatedPower.class);
    }

    @Override
    public void use(AbstractCreature target) {
        useElementPotion();
    }

    @Override
    public int getPotency(final int potency) {
        return POTENCY;
    }

    @Override
    protected void applyElementPower(int amount) {
        DynamicDungeon.applyHeated(amount);
    }

    @Override
    public AbstractPotion makeCopy() {
        return new HeatedPotion();
    }
}

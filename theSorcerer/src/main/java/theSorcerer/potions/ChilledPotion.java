package theSorcerer.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.buff.ChilledPower;
import theSorcerer.powers.buff.HeatedPower;

public class ChilledPotion extends ElementPotion {

    private static final int POTENCY = 2;

    public ChilledPotion() {
        super(ChilledPotion.class, HeatedPower.class, PotionColor.BLUE);
    }

    @Override
    public void initializeData() {
        super.initializeData();
        addTip(ChilledPower.class);
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
        DynamicDungeon.applyChilled(amount);
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ChilledPotion();
    }
}

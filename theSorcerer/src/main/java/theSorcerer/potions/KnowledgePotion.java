package theSorcerer.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.debuff.ElementlessPower;

public class KnowledgePotion extends DynamicPotion {

    public KnowledgePotion() {
        super(KnowledgePotion.class, PotionRarity.COMMON, PotionSize.SPHERE, PotionColor.ANCIENT);
        this.isThrown = false;
    }

    @Override
    public void initializeData() {
        super.initializeData();
        addTip(ElementlessPower.class);
    }

    @Override
    protected void updateDescription() {
        this.description = this.descriptions[0];
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            DynamicDungeon.removeElementless();
        }
    }

    @Override
    public int getPotency(final int potency) {
        return 0;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new KnowledgePotion();
    }
}

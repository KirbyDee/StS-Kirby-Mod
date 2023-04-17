package theSorcerer.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theSorcerer.actions.GiveFlashbackToAllCardsInHandAction;

public class FlashbackPotion extends DynamicPotion {

    public FlashbackPotion() {
        super(FlashbackPotion.class, PotionRarity.UNCOMMON, PotionSize.M, PotionColor.ANCIENT);
        this.isThrown = false;
    }

    @Override
    public void initializeData() {
        super.initializeData();
        addTip("Flashback");
    }

    @Override
    protected void updateDescription() {
        this.description = this.descriptions[0];
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(
                    new GiveFlashbackToAllCardsInHandAction()
            );
        }
    }

    @Override
    public int getPotency(final int potency) {
        return 0;
    }

    @Override
    public AbstractPotion makeCopy() {
        return new FlashbackPotion();
    }
}

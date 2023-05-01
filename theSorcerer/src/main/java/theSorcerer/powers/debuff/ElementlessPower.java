package theSorcerer.powers.debuff;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.cards.DynamicCard;
import theSorcerer.powers.DynamicReducePerTurnPower;

public class ElementlessPower extends DynamicReducePerTurnPower {

    public ElementlessPower(
            final AbstractCreature owner
    ) {
        this(owner, 1);
    }

    public ElementlessPower(
            AbstractCreature owner,
            int amount
    ) {
        super(
                ElementlessPower.class,
                owner,
                amount,
                false
        );

        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        triggerOnElementless();
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        triggerOnElementless();
    }

    private void triggerOnElementless() {
        triggerOnElementless(AbstractDungeon.player.hand);
        triggerOnElementless(AbstractDungeon.player.drawPile);
        triggerOnElementless(AbstractDungeon.player.discardPile);
        triggerOnElementless(AbstractDungeon.player.exhaustPile);
    }

    private void triggerOnElementless(final CardGroup cardGroup) {
        cardGroup.group
                .stream()
                .filter(DynamicCard.class::isInstance)
                .map(DynamicCard.class::cast)
                .forEach(DynamicCard::triggerOnElementless);
    }

    @Override
    public void updateDescription() {
        description = this.descriptions[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ElementlessPower(this.owner);
    }

    @Override
    public PowerType getPowerType() {
        return PowerType.DEBUFF;
    }
}

package theSorcerer.relics;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.special.FireConstruct;
import theSorcerer.cards.special.IceConstruct;

import java.util.ArrayList;

public class ElementalConstruct extends DynamicRelic {

    public ElementalConstruct() {
        super(
                ElementalConstruct.class,
                RelicTier.STARTER,
                LandingSound.MAGICAL
        );
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        addTip("Fire", "Ice");
    }

    @Override
    public void atBattleStart() {
        DynamicDungeon.triggerRelic(this);

        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new FireConstruct());
        choices.add(new IceConstruct());
        addToBot(new ChooseOneAction(choices));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

package theSorcerer.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));

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

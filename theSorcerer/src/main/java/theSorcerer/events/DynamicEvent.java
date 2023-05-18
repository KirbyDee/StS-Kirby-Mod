package theSorcerer.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import theSorcerer.DynamicDungeon;

import static theSorcerer.KirbyDeeMod.makeEventPath;

public abstract class DynamicEvent extends AbstractImageEvent {

    protected final String[] descriptions;
    protected final String[] options;

    public DynamicEvent(
            Class<? extends DynamicEvent> thisClazz
    ) {
        super(
                "",
                CardCrawlGame.languagePack.getEventString(getID(thisClazz)).DESCRIPTIONS[0],
                getImagePath(thisClazz, 1)
        );

        EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(getID(thisClazz));
        this.title = eventStrings.NAME;
        this.descriptions = eventStrings.DESCRIPTIONS;
        this.body = this.descriptions[0];
        this.options = eventStrings.OPTIONS;
        setFirstDialog();
    }

    public static String getID(final Class<? extends DynamicEvent> thisClazz) {
        return DynamicDungeon.makeID(thisClazz);
    }

    private static String getImagePath(final Class<? extends DynamicEvent> thisClazz, final int index) {
        return makeEventPath(thisClazz.getSimpleName() + index + ".jpg");
    }

    protected String getImagePath(final int index) {
        return getImagePath(getClass(), index);
    }

    protected abstract void setFirstDialog();

}

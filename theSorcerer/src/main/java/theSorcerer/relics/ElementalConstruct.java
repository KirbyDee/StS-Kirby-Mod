package theSorcerer.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.KirbyDeeMod;
import theSorcerer.cards.FireConstruct;
import theSorcerer.cards.IceConstruct;
import theSorcerer.util.TextureLoader;

import java.util.ArrayList;

import static theSorcerer.KirbyDeeMod.makeRelicOutlinePath;
import static theSorcerer.KirbyDeeMod.makeRelicPath;

public class ElementalConstruct extends CustomRelic {

    public static final String ID = KirbyDeeMod.makeID("ElementalConstruct");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public ElementalConstruct() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    public void atBattleStart() {
        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new FireConstruct());
        choices.add(new IceConstruct());

        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ChooseOneAction(choices));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

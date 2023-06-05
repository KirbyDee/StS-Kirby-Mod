package theSorcerer.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theSorcerer.DynamicDungeon;
import theSorcerer.powers.debuff.ElementlessPower;

import java.util.List;
import java.util.stream.Collectors;

public class GameController extends DynamicRelic {

    public GameController() {
        super(
                GameController.class,
                RelicTier.RARE,
                LandingSound.FLAT
        );
    }

    @Override
    protected void initializeTips() {
        super.initializeTips();
        addTip(ElementlessPower.class);
    }

    @Override
    public void triggerOnElementless() {
        final List<AbstractCard> groupCopy = AbstractDungeon.player.hand.group
                .stream()
                .filter(this::canBeFreeToPlay)
                .filter(this::isNotInCardQueue)
                .collect(Collectors.toList());

        if (!groupCopy.isEmpty()) {
            DynamicDungeon.triggerRelic(this);
            final AbstractCard card = groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
            card.setCostForTurn(0);
        }
    }

    private boolean canBeFreeToPlay(final AbstractCard card) {
        return card.cost > 0 && card.costForTurn > 0 && !card.freeToPlayOnce;
    }

    private boolean isNotInCardQueue(final AbstractCard card) {
        return AbstractDungeon.actionManager.cardQueue
                .stream()
                .map(i -> i.card)
                .noneMatch(c -> c == card);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}

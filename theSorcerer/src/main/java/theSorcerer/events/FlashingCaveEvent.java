package theSorcerer.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theSorcerer.DynamicDungeon;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.screens.select.GridCardSelectScreenPatch;

public class FlashingCaveEvent extends DynamicEvent {

    private final int goldGain;

    private int goldLoss;

    private CurrentScreen screen = CurrentScreen.ENTRANCE;

    public FlashingCaveEvent() {
        super(FlashingCaveEvent.class);

        if (AbstractDungeon.ascensionLevel >= 15) {
            this.goldLoss = AbstractDungeon.miscRng.random(25, 50);
            this.goldGain = AbstractDungeon.miscRng.random(5, 10);
        }
        else {
            this.goldLoss = AbstractDungeon.miscRng.random(10, 30);
            this.goldGain = AbstractDungeon.miscRng.random(10, 20);
        }

        if (this.goldLoss > AbstractDungeon.player.gold) {
            this.goldLoss = AbstractDungeon.player.gold;
        }
    }

    @Override
    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.sound.play("EVENT_SKULL");
        }
    }


    @Override
    protected void setFirstDialog() {
        this.imageEventText.setDialogOption(this.options[0]);
        this.imageEventText.setDialogOption(this.options[1]);
    }

    @Override
    protected void buttonEffect(final int buttonPressed) {
        switch (this.screen) {
            case ENTRANCE:
                entranceScreen(buttonPressed);
                break;
            case SPIRAL_STAIRS:
                spiralStairsScreen(buttonPressed);
                break;
            case FORK:
                forkScreen();
                break;
            case FLASHBACK_CARD:
                flashbackCardScreen(buttonPressed);
                break;
            case RELIC:
                relicScreen(buttonPressed);
                break;
            case COMPLETE:
                openMap();
        }
    }

    private void entranceScreen(final int buttonPressed) {
        switch (buttonPressed) {
            case 0: // enter
                this.imageEventText.loadImage(getImagePath(2));
                this.imageEventText.updateBodyText(this.descriptions[1]);
                this.imageEventText.updateDialogOption(0, this.options[2] + this.goldLoss + this.options[3]);
                this.imageEventText.updateDialogOption(1, this.options[9] + this.goldGain + this.options[3]);
                this.screen = CurrentScreen.SPIRAL_STAIRS;
                return;
            case 1: // leave
            default:
                this.imageEventText.updateBodyText(this.descriptions[5]);
                this.imageEventText.updateDialogOption(0, this.options[1]);
                this.imageEventText.clearRemainingOptions();
                this.screen = CurrentScreen.COMPLETE;
        }
    }

    private void spiralStairsScreen(final int buttonPressed) {
        switch (buttonPressed) {
            case 0: // continue - lose gold
                this.imageEventText.loadImage(getImagePath(3));
                this.imageEventText.updateBodyText(this.descriptions[2]);
                AbstractDungeon.player.loseGold(this.goldLoss);
                if (Settings.AMBIANCE_ON) {
                    CardCrawlGame.sound.play("GOLD_JINGLE");
                }
                this.imageEventText.updateDialogOption(0, this.options[4]);
                this.imageEventText.updateDialogOption(1, this.options[5]);
                this.screen = CurrentScreen.FORK;
                return;
            case 1: // leave
            default:
                AbstractDungeon.player.gainGold(this.goldGain);
                if (Settings.AMBIANCE_ON) {
                    CardCrawlGame.sound.play("GOLD_JINGLE");
                }
                this.imageEventText.updateBodyText(this.descriptions[10]);
                this.imageEventText.updateDialogOption(0, this.options[1]);
                this.imageEventText.clearRemainingOptions();
                this.screen = CurrentScreen.COMPLETE;
        }
    }

    private void forkScreen() {
        // random chosen direction
        final int direction = AbstractDungeon.miscRng.random.nextInt(2);

        switch (direction) {
            case 0: // flashback
                final boolean canMakeCardFlashback = AbstractDungeon.player.masterDeck.group.stream().anyMatch(DynamicDungeon::canMakeCardFlashback);
                this.imageEventText.loadImage(getImagePath(5));
                this.imageEventText.updateBodyText(this.descriptions[3]);
                if (Settings.AMBIANCE_ON) {
                    CardCrawlGame.sound.play("EVENT_FORGOTTEN");
                }
                this.imageEventText.updateDialogOption(0, this.options[6], !canMakeCardFlashback);
                this.imageEventText.updateDialogOption(1, this.options[1]);
                this.screen = CurrentScreen.FLASHBACK_CARD;
                return;
            case 1: // ladder
            default:
                this.imageEventText.loadImage(getImagePath(4));
                this.imageEventText.updateBodyText(this.descriptions[4]);
                this.imageEventText.updateDialogOption(0, this.options[8]);
                this.imageEventText.updateDialogOption(1, this.options[1]);
                this.screen = CurrentScreen.RELIC;
        }
    }

    private void flashbackCardScreen(final int buttonPressed) {
        switch (buttonPressed) {
            case 0: // pick up flashback
                this.imageEventText.updateBodyText(this.descriptions[6]);
                this.imageEventText.updateDialogOption(0, this.options[1]);
                this.imageEventText.clearRemainingOptions();
                flashback();
                this.screen = CurrentScreen.COMPLETE;
                return;
            case 1: // leave
            default:
                this.imageEventText.updateBodyText(this.descriptions[7]);
                this.imageEventText.updateDialogOption(0, this.options[1]);
                this.imageEventText.clearRemainingOptions();
                this.screen = CurrentScreen.COMPLETE;
        }
    }

    private void relicScreen(final int buttonPressed) {
        switch (buttonPressed) {
            case 0: // pick up relic
                this.imageEventText.updateBodyText(this.descriptions[8]);
                this.imageEventText.updateDialogOption(0, this.options[1]);
                this.imageEventText.clearRemainingOptions();
                AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), r);
                this.screen = CurrentScreen.COMPLETE;
                return;
            case 1: // leave
            default:
                this.imageEventText.updateBodyText(this.descriptions[9]);
                this.imageEventText.updateDialogOption(0, this.options[1]);
                this.imageEventText.clearRemainingOptions();
                this.screen = CurrentScreen.COMPLETE;
        }
    }

    private void flashback() {
        GridCardSelectScreenPatch.open(
                DynamicDungeon.filterCardGroupBy(
                        AbstractDungeon.player.masterDeck,
                        DynamicDungeon::canMakeCardFlashback
                ),
                1,
                this.options[7],
                false,
                false,
                false,
                false,
                DynamicDungeon::makeCardFlashback
        );
    }

    @Override
    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractCardPatch.flashback.set(card, true);
            DynamicDungeon.modifyCardInDeck(card);
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    private enum CurrentScreen {
        ENTRANCE,
        SPIRAL_STAIRS,
        FORK,
        FLASHBACK_CARD,
        RELIC,
        COMPLETE
    }
}

package theSorcerer.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.screens.select.GridCardSelectScreenPatch;
import theSorcerer.relics.ElementalMaster;
import theSorcerer.relics.ElementalPets;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ElementalCreaturesEvent extends DynamicEvent {

    private final int damage;

    private CardModifier cardModifier;

    private final int goldGain;
    
    private CurrentScreen screen = CurrentScreen.INTRO;

    public ElementalCreaturesEvent() {
        super(ElementalCreaturesEvent.class);

        if (AbstractDungeon.ascensionLevel >= 15) {
            this.damage = (int)((float)AbstractDungeon.player.maxHealth * 0.35F);
            this.goldGain = AbstractDungeon.miscRng.random(20, 50);
        }
        else {
            this.damage = (int)((float)AbstractDungeon.player.maxHealth * 0.25F);
            this.goldGain = AbstractDungeon.miscRng.random(35, 75);
        }
    }

    @Override
    public void onEnterRoom() {
        if (Settings.AMBIANCE_ON) {
            CardCrawlGame.music.playTempBGM("ELITE");
        }
    }


    @Override
    protected void setFirstDialog() {
        this.imageEventText.setDialogOption(this.options[0]);
        this.imageEventText.setDialogOption(this.options[1]);
        this.imageEventText.setDialogOption(this.options[2]);
    }

    @Override
    protected void buttonEffect(final int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                initialScreen(buttonPressed);
                break;
            case FIGHT:
                fightScreen();
                break;
            case FLEE:
                fleeScreen();
                break;
            case MORPHOSE:
                elementmorphoseScreen(buttonPressed);
                break;
            case COMPLETE:
                CardCrawlGame.music.fadeOutTempBGM();
                openMap();
        }
    }

    private void initialScreen(final int buttonPressed) {
        switch (buttonPressed) {
            case 0: // fight
                String description = this.descriptions[1];
                String option = this.options[6] + this.damage + this.options[7] + this.goldGain + this.options[14];
                CurrentScreen nextScreen = CurrentScreen.FIGHT;
                if (DynamicDungeon.hasRelic(ElementalMaster.class)) {
                    description = this.descriptions[6];
                    option = this.options[13] + this.goldGain + this.options[14];
                    nextScreen = CurrentScreen.FLEE;
                }
                this.imageEventText.updateBodyText(description);
                this.imageEventText.updateDialogOption(0, option);
                this.imageEventText.clearRemainingOptions();
                this.screen = nextScreen;
                return;
            case 1: // hide
                this.imageEventText.loadImage(getImagePath(2));
                if (Settings.AMBIANCE_ON) {
                    CardCrawlGame.music.fadeOutTempBGM();
                    CardCrawlGame.music.playTempBGM("SHRINE");
                }
                final boolean canMakeCardFire = AbstractDungeon.player.masterDeck.group.stream().anyMatch(DynamicDungeon::canMakeCardFire);
                final boolean canMakeCardIce = AbstractDungeon.player.masterDeck.group.stream().anyMatch(DynamicDungeon::canMakeCardIce);
                this.imageEventText.updateBodyText(this.descriptions[2]);
                this.imageEventText.updateDialogOption(0, this.options[3], !canMakeCardFire);
                this.imageEventText.updateDialogOption(1, this.options[4], !canMakeCardIce);
                String befriendOption = this.options[11];
                boolean canClick = true;
                if (DynamicDungeon.hasRelic(ElementalMaster.class)) {
                    befriendOption = this.options[10];
                    canClick = false;
                }
                if (DynamicDungeon.hasRelic(ElementalPets.class)) {
                    befriendOption = this.options[12];
                    canClick = true;
                }
                this.imageEventText.updateDialogOption(2, befriendOption, canClick);
                this.imageEventText.updateDialogOption(3, this.options[5]);
                this.screen = CurrentScreen.MORPHOSE;
                return;
            case 2: // leave
            default:
                this.imageEventText.updateBodyText(this.descriptions[3]);
                this.imageEventText.updateDialogOption(0, this.options[2]);
                this.imageEventText.clearRemainingOptions();
                this.screen = CurrentScreen.COMPLETE;
        }
    }

    private void fightScreen() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.MED, false);
        CardCrawlGame.sound.play("BLUNT_FAST");
        AbstractDungeon.player.damage(new DamageInfo(null, this.damage));
        AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldGain));
        AbstractDungeon.player.gainGold(this.goldGain);
        this.imageEventText.updateDialogOption(0, this.options[2]);
        this.imageEventText.clearRemainingOptions();
        this.screen = CurrentScreen.COMPLETE;
    }

    private void fleeScreen() {
        AbstractDungeon.effectList.add(new RainingGoldEffect(this.goldGain));
        AbstractDungeon.player.gainGold(this.goldGain);
        this.imageEventText.updateDialogOption(0, this.options[2]);
        this.imageEventText.clearRemainingOptions();
        this.screen = CurrentScreen.COMPLETE;
    }

    private void elementmorphoseScreen(final int buttonPressed) {
        switch (buttonPressed) {
            case 0: // touch flame
                this.imageEventText.updateBodyText(this.descriptions[4]);
                this.imageEventText.updateDialogOption(0, this.options[2]);
                this.imageEventText.clearRemainingOptions();
                firemorphose();
                this.screen = CurrentScreen.COMPLETE;
                return;
            case 1: // touch icicle
                this.imageEventText.updateBodyText(this.descriptions[4]);
                this.imageEventText.updateDialogOption(0, this.options[2]);
                this.imageEventText.clearRemainingOptions();
                icemorphose();
                this.screen = CurrentScreen.COMPLETE;
                return;
            case 2: // befriend
                this.imageEventText.updateBodyText(this.descriptions[5]);
                this.imageEventText.updateDialogOption(0, this.options[2]);
                this.imageEventText.clearRemainingOptions();
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain((float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2), new ElementalPets());
                this.screen = CurrentScreen.COMPLETE;
                return;
            case 3: // ignore
            default:
                this.imageEventText.updateBodyText(this.descriptions[3]);
                this.imageEventText.updateDialogOption(0, this.options[2]);
                this.imageEventText.clearRemainingOptions();
                this.screen = CurrentScreen.COMPLETE;
        }
    }

    private void firemorphose() {
        this.cardModifier = CardModifier.FIRE;
        elementmorphose(this.options[8], DynamicDungeon::canMakeCardFire, DynamicDungeon::makeCardFire);
    }

    private void icemorphose() {
        this.cardModifier = CardModifier.ICE;
        elementmorphose(this.options[9], DynamicDungeon::canMakeCardIce, DynamicDungeon::makeCardIce);
    }


    private void elementmorphose(
            final String text,
            final Predicate<AbstractCard> canMakeElementCard,
            final Consumer<AbstractCard> applyElementToCard
    ) {
        GridCardSelectScreenPatch.open(
                DynamicDungeon.filterCardGroupBy(
                        AbstractDungeon.player.masterDeck,
                        canMakeElementCard
                ),
                1,
                text,
                false,
                false,
                false,
                false,
                applyElementToCard
        );
    }

    @Override
    public void update() {
        super.update();
        if (!AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && this.cardModifier != null) {
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            if (this.cardModifier == CardModifier.FIRE) {
                AbstractCardPatch.fire.set(card, true);
            }
            else if (this.cardModifier == CardModifier.ICE) {
                AbstractCardPatch.ice.set(card, true);
            }
            DynamicDungeon.modifyCardInDeck(card);
            AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    private enum CurrentScreen {
        INTRO,
        FIGHT,
        MORPHOSE,
        FLEE,
        COMPLETE;
    }
}

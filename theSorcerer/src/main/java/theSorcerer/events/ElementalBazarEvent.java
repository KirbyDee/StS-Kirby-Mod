package theSorcerer.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.curse.Elementaldysphoria;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.screens.select.GridCardSelectScreenPatch;
import theSorcerer.relics.BurningSoul;
import theSorcerer.relics.FreezingSoul;

public class ElementalBazarEvent extends DynamicEvent {

    private static final float GOLD_SALE_RATE = 0.5f;

    private static final float ASCENSION_RATE = 1.5f;

    private static final int GOLD_STEAL_MIN = 50;

    private static final int GOLD_STEAL_MAX = 80;

    private static final int NUMBER_OF_CARDS_TO_CHOOSE_FROM = 5;

    private final boolean hasBurningSoul;

    private final boolean hasFreezingSoul;

    private final int stealGold;

    private int buyGold = 40;

    private int modifyGold = 50;

    private int upgradeGold = 50;

    private Chosen chosen = Chosen.NONE;

    private CurrentScreen screen = CurrentScreen.INTRO;

    public ElementalBazarEvent() {
        super(ElementalBazarEvent.class);
        this.hasBurningSoul = DynamicDungeon.hasRelic(BurningSoul.class);
        this.hasFreezingSoul = DynamicDungeon.hasRelic(FreezingSoul.class);

        if (AbstractDungeon.ascensionLevel >= 15) {
            this.buyGold = (int) (this.buyGold * ASCENSION_RATE);
            this.modifyGold = (int) (this.modifyGold * ASCENSION_RATE);
            this.upgradeGold = (int) (this.upgradeGold * ASCENSION_RATE);
        }

        switch (AbstractDungeon.miscRng.random.nextInt(4)) {
            case 0:
                this.buyGold = (int) (this.buyGold * GOLD_SALE_RATE);
                break;
            case 1:
                this.modifyGold = (int) (this.modifyGold * GOLD_SALE_RATE);
                break;
            case 2:
                this.upgradeGold = (int) (this.upgradeGold * GOLD_SALE_RATE);
                break;
            default:
        }

        this.stealGold = AbstractDungeon.miscRng.random(GOLD_STEAL_MIN, GOLD_STEAL_MAX);
    }

    @Override
    protected void setFirstDialog() {
        this.imageEventText.setDialogOption(this.options[17]);
        this.imageEventText.setDialogOption(this.options[12]);
    }

    @Override
    protected void buttonEffect(final int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                introScreen(buttonPressed);
                break;
            case SHOP:
                shopScreen(buttonPressed);
                break;
            case SOUL:
                soulScreen(buttonPressed);
                break;
            case COMPLETE:
                CardCrawlGame.music.fadeOutTempBGM();
                openMap();
        }
    }

    private void introScreen(final int buttonPressed) {
        switch (buttonPressed) {
            case 0: // continue
                setupShopScreen();
                return;
            case 1: // leave
            default:
                this.imageEventText.updateBodyText(this.descriptions[2]);
                this.imageEventText.updateDialogOption(0, this.options[12]);
                this.imageEventText.clearRemainingOptions();
                this.screen = CurrentScreen.COMPLETE;
        }
    }

    private void setupShopScreen() {
        this.imageEventText.updateBodyText(this.descriptions[1]);

        // buy
        this.imageEventText.updateDialogOption(0, this.options[0] + this.buyGold + this.options[10], AbstractDungeon.player.gold < this.buyGold);

        // modify
        this.imageEventText.updateDialogOption(1, this.options[1] + this.modifyGold + this.options[10], AbstractDungeon.player.gold < this.modifyGold);

        // upgrade
        this.imageEventText.updateDialogOption(2, this.options[2] + this.upgradeGold + this.options[10], AbstractDungeon.player.gold < this.upgradeGold);

        // trade
        if (this.hasBurningSoul) {
            this.imageEventText.updateDialogOption(3, this.options[4]);
        }
        else if (this.hasFreezingSoul) {
            this.imageEventText.updateDialogOption(3, this.options[5]);
        }
        else {
            this.imageEventText.updateDialogOption(3, this.options[3], true);
        }

        // steal
        this.imageEventText.updateDialogOption(4, this.options[8] + this.stealGold + this.options[10] + this.options[9], new Elementaldysphoria());

        // leave
        this.imageEventText.updateDialogOption(5, this.options[12]);

        // shop screen
        this.screen = CurrentScreen.SHOP;
    }

    private void shopScreen(final int buttonPressed) {
        switch (buttonPressed) {
            case 0: // buy
                AbstractDungeon.player.loseGold(this.buyGold);
                this.imageEventText.updateBodyText(this.descriptions[4]);
                this.imageEventText.updateDialogOption(0, this.options[12]);
                this.imageEventText.clearRemainingOptions();
                addCardToDeck();
                this.screen = CurrentScreen.COMPLETE;
                return;
            case 1: // modify
                AbstractDungeon.player.loseGold(this.modifyGold);
                this.imageEventText.updateBodyText(this.descriptions[4]);
                this.imageEventText.updateDialogOption(0, this.options[12]);
                this.imageEventText.clearRemainingOptions();
                modifyCardInDeck();
                this.screen = CurrentScreen.COMPLETE;
                return;
            case 2: // upgrade
                AbstractDungeon.player.loseGold(this.upgradeGold);
                this.imageEventText.updateBodyText(this.descriptions[4]);
                this.imageEventText.updateDialogOption(0, this.options[12]);
                this.imageEventText.clearRemainingOptions();
                ugradeCardInDeck();
                this.screen = CurrentScreen.COMPLETE;
                return;
            case 3: // show soul
                this.imageEventText.updateBodyText(this.descriptions[5]);
                this.imageEventText.clearRemainingOptions();
                this.imageEventText.updateDialogOption(0, this.options[this.hasBurningSoul ? 6 : 7]);
                this.imageEventText.updateDialogOption(1, this.options[13]);
                this.screen = CurrentScreen.SOUL;
                return;
            case 4: // steal
                AbstractDungeon.player.gainGold(this.stealGold);
                if (Settings.AMBIANCE_ON) {
                    CardCrawlGame.sound.play("GOLD_JINGLE");
                }
                AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new Elementaldysphoria(), (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                this.imageEventText.updateBodyText(this.descriptions[3]);
                this.imageEventText.updateDialogOption(0, this.options[12]);
                this.imageEventText.clearRemainingOptions();
                this.screen = CurrentScreen.COMPLETE;
                return;
            case 5: // leave
            default:
                this.imageEventText.updateBodyText(this.descriptions[2]);
                this.imageEventText.updateDialogOption(0, this.options[12]);
                this.imageEventText.clearRemainingOptions();
                this.screen = CurrentScreen.COMPLETE;
        }
    }

    private void soulScreen(final int buttonPressed) {
        switch (buttonPressed) {
            case 0: // trade
                this.imageEventText.updateBodyText(this.descriptions[6]);
                this.imageEventText.updateDialogOption(0, this.options[12]);
                this.imageEventText.clearRemainingOptions();
                DynamicDungeon.withRelicDo(BurningSoul.class, r -> AbstractDungeon.player.loseRelic(r.relicId));
                DynamicDungeon.withRelicDo(FreezingSoul.class, r -> AbstractDungeon.player.loseRelic(r.relicId));
                obtainRareRelic();
                obtainRareRelic();
                this.screen = CurrentScreen.COMPLETE;
                return;
            case 1: // back
            default:
                setupShopScreen();
        }
    }

    private void obtainRareRelic() {
        AbstractRelic relic = AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE);
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, relic);
    }

    private void addCardToDeck() {
        this.chosen = Chosen.BUY;
        CardGroup cardGroup = DynamicDungeon.returnRandomCards(NUMBER_OF_CARDS_TO_CHOOSE_FROM, DynamicDungeon::isElementCard);
        AbstractDungeon.gridSelectScreen.open(cardGroup, 1, this.options[14], false);
    }

    private void modifyCardInDeck() {
        this.chosen = Chosen.MODIFY;
        GridCardSelectScreenPatch.open(
                DynamicDungeon.filterCardGroupBy(
                        AbstractDungeon.player.masterDeck,
                        DynamicDungeon::canMakeCardElementCost
                ),
                1,
                this.options[14],
                false,
                false,
                false,
                false,
                DynamicDungeon::makeCardElementCost
        );
    }

    private void ugradeCardInDeck() {
        this.chosen = Chosen.UPGRADE;
        AbstractDungeon.gridSelectScreen.open(
                AbstractDungeon.player.masterDeck.getUpgradableCards(),
                1,
                this.options[16],
                true,
                false,
                false,
                false
        );
    }

    @Override
    public void update() {
        super.update();
        if (this.chosen != Chosen.NONE && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard chosenCard = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            switch (this.chosen) {
                case BUY:
                    onBuyCard(chosenCard);
                    break;
                case MODIFY:
                    onModifyCard(chosenCard);
                    break;
                case UPGRADE:
                    onUpgradeCard(chosenCard);
                    break;
                default:
                    break;
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.chosen = Chosen.NONE;
        }
    }

    private void onBuyCard(final AbstractCard chosenCard) {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(chosenCard.makeCopy(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
    }

    private void onModifyCard(final AbstractCard card) {
        AbstractCardPatch.elementalCost.set(card, true);
        DynamicDungeon.modifyCardInDeck(card);
        AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));

    }

    private void onUpgradeCard(final AbstractCard card) {
        card.upgrade();
        AbstractDungeon.player.bottledCardUpgradeCheck(card);
        AbstractDungeon.effectsQueue.add(new ShowCardBrieflyEffect(card.makeStatEquivalentCopy()));
        AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
    }

    private enum CurrentScreen {
        INTRO,
        SHOP,
        SOUL,
        COMPLETE;
    }

    private enum Chosen {
        NONE,
        BUY,
        MODIFY,
        UPGRADE
    }
}

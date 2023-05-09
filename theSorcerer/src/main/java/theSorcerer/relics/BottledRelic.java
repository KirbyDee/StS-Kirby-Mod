package theSorcerer.relics;

import basemod.abstracts.CustomBottleRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theSorcerer.DynamicDungeon;
import theSorcerer.patches.cards.AbstractCardPatch;

public abstract class BottledRelic extends DynamicRelic implements CustomBottleRelic {

    private boolean cardSelected = true;

    protected AbstractCard card;

    public BottledRelic(
            Class<? extends BottledRelic> thisClazz,
            RelicTier tier
    ) {
        super(
                thisClazz,
                tier,
                LandingSound.CLINK
        );
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public AbstractCard getCard() {
        return this.card.makeCopy();
    }

    @Override
    public void onEquip() {
        CardGroup cardGroup = getNonBottledCards();
        if (AbstractDungeon.player.masterDeck.size() > 0) {
            this.cardSelected = false;
            if (AbstractDungeon.isScreenUp) {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }

            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.INCOMPLETE;
            AbstractDungeon.gridSelectScreen.open(cardGroup, 1, this.DESCRIPTIONS[1] + this.name + LocalizedStrings.PERIOD, false, false, false, false);
        }
    }

    protected abstract CardGroup getCardGroup();

    private CardGroup getNonBottledCards() {
        CardGroup cardGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        getCardGroup().group
                .stream()
                .filter(c -> !c.inBottleFlame && !c.inBottleLightning && !c.inBottleTornado && !AbstractCardPatch.inBottleGhost.get(c) && !AbstractCardPatch.inBottleEnergy.get(c))
                .forEach(c -> cardGroup.group.add(c));
        return cardGroup;
    }

    @Override
    public void onUnequip() {
        if (this.card != null) {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(this.card);
            if (cardInDeck != null) {
                onRemoveBottledCard(cardInDeck);
            }
        }
    }

    protected abstract void onRemoveBottledCard(final AbstractCard card);

    @Override
    public void update() {
        super.update();
        if (!this.cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            this.cardSelected = true;
            this.card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            onAddBottledCard(this.card);
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            setDescriptionAfterLoading();
        }
    }

    protected abstract void onAddBottledCard(final AbstractCard card);

    public void setDescriptionAfterLoading() {
        this.description = this.DESCRIPTIONS[2] + FontHelper.colorString(this.card.name, "y") + this.DESCRIPTIONS[3];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public void atBattleStart() {
        DynamicDungeon.triggerRelic(this);
    }

    @Override
    public boolean canSpawn() {
        return getCardGroup().group.size() > 0;
    }
}

package theSorcerer.cards.status;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.modifiers.CardModifier;

public class Frostbite extends DynamicCard {

    // --- VALUES START ---
    private static final int DAMAGE = 2;
    private static final int UPGRADE_DAMAGE = 2;
    // --- VALUES END ---

    public Frostbite() {
        super(
                DynamicCard.InfoBuilder(Frostbite.class)
                        .type(CardType.STATUS)
                        .rarity(CardRarity.COMMON)
                        .magicNumber(DAMAGE)
                        .modifiers(CardModifier.UNPLAYABLE)
                        .build()
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.dontTriggerOnUseCard) {
            this.addToBot(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(
                new CardQueueItem(this, true)
        );
    }

    @Override
    protected void upgradeValues() {
        upgradeMagicNumber(UPGRADE_DAMAGE);
    }
}

package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import theSorcerer.patches.cards.AbstractCardPatch;
import theSorcerer.patches.cards.CardAbility;

public class ForgottenConduit extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = -2;
    private static final int ENERGY_GAIN = 2;
    private static final int DISCARD_NUMBER = 1;
    // --- VALUES END ---

    public ForgottenConduit() {
        super(
                DynamicCard.InfoBuilder(ForgottenConduit.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.NONE)
                        .magicNumber(ENERGY_GAIN)
                        .abilities(CardAbility.UNPLAYABLE, CardAbility.FLASHBACK)
        );
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}

    @Override
    public void triggerOnFlashback() {
        // exhaust card
        AbstractDungeon.player.hand.moveToExhaustPile(this);

        // exhaust a (random) card in hand
        addToBot(
                new ExhaustAction(
                        AbstractDungeon.player,
                        AbstractDungeon.player,
                        DISCARD_NUMBER,
                        !this.upgraded
                )
        );

        // gain 2(3) energy
        addToBot(new GainEnergyAction(this.magicNumber));
    }
}

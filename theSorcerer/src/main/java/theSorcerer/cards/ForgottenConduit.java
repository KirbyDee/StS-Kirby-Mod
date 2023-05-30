package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;

public class ForgottenConduit extends SorcererCard {

    // --- VALUES START ---
    private static final int ENERGY_GAIN = 2;
    private static final int EXHAUST_NUMBER = 1;
    // --- VALUES END ---

    public ForgottenConduit() {
        super(
                DynamicCard.InfoBuilder(ForgottenConduit.class)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.UNCOMMON)
                        .target(CardTarget.NONE)
                        .magicNumber(ENERGY_GAIN)
                        .secondMagicNumber(EXHAUST_NUMBER)
                        .modifiers(CardModifier.UNPLAYABLE, CardModifier.FLASHBACK)
        );
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {}

    @Override
    public void triggerOnFlashback() {
        superFlash();

        // exhaust card
        AbstractDungeon.player.hand.moveToExhaustPile(this);

        // exhaust a (random) card in hand
        addToBot(
                new ExhaustAction(
                        AbstractDungeon.player,
                        AbstractDungeon.player,
                        this.secondMagicNumber,
                        !this.upgraded
                )
        );

        // gain 2(3) energy
        DynamicDungeon.gainEnergy(this.magicNumber);
    }
}

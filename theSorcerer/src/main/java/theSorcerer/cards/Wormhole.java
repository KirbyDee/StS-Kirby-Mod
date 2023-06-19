package theSorcerer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.modifiers.CardModifier;

public class Wormhole extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = -2;
    // --- VALUES END ---

    public Wormhole() {
        super(
                DynamicCard.InfoBuilder(Wormhole.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.RARE)
                        .modifiers(CardModifier.COPYCAT, CardModifier.UNPLAYABLE)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {}

    @Override
    public void upgradeValues() {
        DynamicDungeon.removeModifierFromCard(this, CardModifier.COPYCAT);
        DynamicDungeon.addModifierToCard(this, CardModifier.COPYCAT_PLUS);
    }

}

package theSorcerer.cards.curse;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.DynamicDungeon;
import theSorcerer.cards.DynamicCard;
import theSorcerer.modifiers.CardModifier;

public class Elementaldysphoria extends DynamicCard {

    public Elementaldysphoria() {
        super(
                DynamicCard.InfoBuilder(Elementaldysphoria.class)
                        .type(CardType.CURSE)
                        .color(CardColor.CURSE)
                        .rarity(CardRarity.CURSE)
                        .modifiers(CardModifier.AUTO)
                        .build()
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        DynamicDungeon.applyElementless();
    }

    @Override
    public void upgrade() {}
}

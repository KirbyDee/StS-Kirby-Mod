package theSorcerer.cards.special;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;

public class Sheep extends DynamicCard {

    public Sheep() {
        super(
                DynamicCard.InfoBuilder(Sheep.class)
                        .type(CardType.POWER)
                        .rarity(CardRarity.SPECIAL)
                        .build()
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        // cannot play
    }
}

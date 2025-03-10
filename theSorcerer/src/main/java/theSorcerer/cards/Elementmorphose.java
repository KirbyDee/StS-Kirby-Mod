package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.special.BaptismOfFire;
import theSorcerer.cards.special.Crystalize;
import theSorcerer.modifiers.CardModifier;

import java.util.ArrayList;

public class Elementmorphose extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    // --- VALUES END ---

    public Elementmorphose() {
        super(
                DynamicCard.InfoBuilder(Elementmorphose.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.RARE)
                        .modifiers(CardModifier.EXHAUST)
        );
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> choices = new ArrayList<>();
        choices.add(new BaptismOfFire());
        choices.add(new Crystalize());

        if (this.upgraded) {
            choices.forEach(AbstractCard::upgrade);
        }

        addToBot(new ChooseOneAction(choices));
    }
}

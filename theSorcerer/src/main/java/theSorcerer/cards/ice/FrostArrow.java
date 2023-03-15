package theSorcerer.cards.ice;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.SorcererCardTags;
import theSorcerer.cards.status.Frostbite;

public class FrostArrow extends SorcererIceCard { // TODO: rename and different card

    // --- VALUES START ---
    private static final int COST = 0;
    private static final int DAMAGE = 3;
    // --- VALUES END ---

    public FrostArrow() {
        super(
                DynamicCard.InfoBuilder(FrostArrow.class)
                        .cost(COST)
                        .type(CardType.ATTACK)
                        .rarity(CardRarity.COMMON)
                        .target(CardTarget.ENEMY)
                        .damage(DAMAGE)
        );

        this.cardsToPreview = new Frostbite();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(
                new DamageAction(
                        m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
                )
        );

        addToBot(
                new MakeTempCardInDiscardAction(new Frostbite(), 1)
        );
    }
}

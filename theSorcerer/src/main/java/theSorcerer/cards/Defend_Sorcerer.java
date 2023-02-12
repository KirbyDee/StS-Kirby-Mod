package theSorcerer.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Defend_Sorcerer extends AbstractCard {
    public static final String ID = "Defend_R";
    private static final CardStrings cardStrings;

    public Defend_Sorcerer() {
        super("Defend_R", cardStrings.NAME, "red/skill/defend", 1, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.BASIC, CardTarget.SELF);
        this.baseBlock = 5;
        this.tags.add(CardTags.STARTER_DEFEND);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (Settings.isDebug) {
            this.addToBot(new GainBlockAction(p, p, 50));
        } else {
            this.addToBot(new GainBlockAction(p, p, this.block));
        }

    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }

    }

    public AbstractCard makeCopy() {
        return new Defend_Red();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Defend_R");
    }
}
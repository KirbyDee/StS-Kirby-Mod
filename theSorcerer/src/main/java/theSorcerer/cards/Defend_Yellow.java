package theSorcerer.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.fire.FireDefend;
import theSorcerer.cards.ice.IceDefend;

public class Defend_Yellow extends SorcererCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BLOCK = 3;
    // --- VALUES END ---

    public Defend_Yellow() {
        super(
                DynamicCard.InfoBuilder(Defend_Yellow.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.BASIC)
                        .target(CardTarget.SELF)
                        .tags(CardTags.STARTER_DEFEND)
                        .block(BLOCK)
        );
    }

    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new GainBlockAction(
                        player,
                        player,
                        this.block
                )
        );
    }

    @Override
    public void triggerOnMakeFire() {
        CustomCard fireStrike = new FireDefend();
        this.name = fireStrike.name;
        this.loadCardImage(fireStrike.textureImg);
    }

    @Override
    public void triggerOnMakeIce() {
        CustomCard iceStrike = new IceDefend();
        this.name = iceStrike.name;
        this.loadCardImage(iceStrike.textureImg);
    }

    public void upgradeValues() {
        upgradeBlock(UPGRADE_BLOCK);
    }
}
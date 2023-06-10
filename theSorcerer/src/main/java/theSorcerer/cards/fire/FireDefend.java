package theSorcerer.cards.fire;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.ice.IceDefend;
import theSorcerer.cards.ice.SorcererIceCard;

public class FireDefend extends SorcererIceCard {

    // --- VALUES START ---
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    // --- VALUES END ---

    public FireDefend() {
        super(
                DynamicCard.InfoBuilder(FireDefend.class)
                        .cost(COST)
                        .type(CardType.SKILL)
                        .rarity(CardRarity.BASIC)
                        .target(CardTarget.SELF)
                        .tags(CardTags.STARTER_DEFEND)
                        .block(BLOCK)
        );
    }

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {
        addToBot(
                new GainBlockAction(
                        player,
                        player,
                        this.block
                )
        );

        CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1F);
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

    @Override
    protected void upgradeValues() {
        upgradeBlock(UPGRADE_PLUS_BLOCK);
    }
}

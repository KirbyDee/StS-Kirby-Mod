package theSorcerer.cards.fire;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theSorcerer.cards.DynamicCard;
import theSorcerer.cards.arcane.ArcaneDefend;
import theSorcerer.cards.ice.IceDefend;

public class FireDefend extends SorcererFireCard {

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
    }

    @Override
    public void triggerOnMakeFire() {
        triggerOnMorphose(new FireDefend());
    }

    @Override
    public void triggerOnMakeIce() {
        triggerOnMorphose(new IceDefend());
    }

    @Override
    public void triggerOnMakeArcane() {
        triggerOnMorphose(new ArcaneDefend());
    }

    private void triggerOnMorphose(final CustomCard defend) {
        this.name = defend.name;
        this.loadCardImage(defend.textureImg);
    }

    @Override
    protected void upgradeValues() {
        upgradeBlock(UPGRADE_PLUS_BLOCK);
    }
}

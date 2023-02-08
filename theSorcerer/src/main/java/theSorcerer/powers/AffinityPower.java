package theSorcerer.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theSorcerer.util.TextureLoader;

import static theSorcerer.KirbyDeeMod.makePowerPath;

public abstract class AffinityPower extends AbstractPower implements CloneablePowerInterface {

    // TODO
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));
    private final PowerStrings powerStrings;
    private final String powerName;

    public AffinityPower(
            final AbstractCreature owner,
            final int amount,
            final String powerId,
            final String powerName
    ) {
        this.powerStrings = CardCrawlGame.languagePack.getPowerStrings(powerId);
        this.name = powerStrings.NAME;
        this.ID = powerId;
        this.powerName = powerName;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.canGoNegative = false;

        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            removeSelf();
        }

        if (this.amount >= 999) {
            this.amount = 999;
        }
    }

    @Override
    public void atEndOfRound() {
        reducePowerToZero();
    }

    protected void reducePowerToZero() {
        flash(); // Makes the power icon flash.
        addToTop(
                new ReducePowerAction(
                        this.owner,
                        this.owner,
                        this.ID,
                        this.amount
                )
        );
    }

    protected void removeSelf() {
        addToTop(
                new RemoveSpecificPowerAction(
                        this.owner,
                        this.owner,
                        this.powerName
                )
        );
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + "3" + powerStrings.DESCRIPTIONS[1]; // TODO
    }
}

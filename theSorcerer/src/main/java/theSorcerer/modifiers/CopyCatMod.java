package theSorcerer.modifiers;

import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class CopyCatMod extends RetainMod {

    private boolean copycat = false;

    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        CardModifierManager.removeModifiersById(card, RetainMod.ID, true);
    }

    @Override
    public Color getGlow(AbstractCard card) {
        return Color.VIOLET.cpy();
    }

    @Override
    public void onDrawn(AbstractCard card) {
        this.copycat = true;
    }

    @Override
    public void onRetained(AbstractCard card) {
        this.copycat = true;
    }

    public boolean isCopycat() {
        return this.copycat;
    }

    public void setCopycat(boolean copycat) {
        this.copycat = copycat;
    }
}

package theSorcerer.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.cardmods.InnateMod;
import basemod.cardmods.RetainMod;

public enum CardModifier {
    ELEMENTCOST(new ElementalCostMod()),
    ARCANE(new ArcaneMod()),
    FIRE(new FireMod()),
    ICE(new IceMod()),
    AUTO(new AutoMod()),
    UNPLAYABLE(new UnplayableMod()),
    ETHEREAL(new EtherealMod()),
    RETAIN(new RetainMod()),
    INNATE(new InnateMod()),
    ENTOMB(new EntombMod()),
    EXHAUST(new ExhaustMod()),
    FUTURITY(new FuturityMod()),
    FLASHBACK(new FlashbackMod());

    public final AbstractCardModifier cardMod;

    CardModifier(final AbstractCardModifier cardMod) {
        this.cardMod = cardMod;
    }
}

package jedi.relics;

// If you wonder how this works, go check paper patches in patches folder.

import basemod.helpers.RelicType;

public class PaperFaux
    extends AbstractJediRelic
{
    public static final String ID = "jedi:paperfaux";
    public static final float dmgMod = 0.25F;

    public PaperFaux() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK, RelicType.BLUE);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }
}

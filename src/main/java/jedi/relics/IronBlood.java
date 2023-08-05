package jedi.relics;

import basemod.helpers.RelicType;

public class IronBlood
    extends AbstractJediRelic
{
    public static final String ID = "jedi:ironblood";

    public IronBlood()
    {
        super(ID, RelicTier.SHOP, LandingSound.CLINK, RelicType.RED);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}

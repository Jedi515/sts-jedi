package jedi.relics;

public class IronBlood
    extends AbstractJediRelic
{
    public static final String ID = "jedi:ironblood";

    public IronBlood()
    {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}

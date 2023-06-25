package jedi.relics;

public class TokenOfGlory
    extends AbstractJediRelic
{
    public static final String ID = "jedi:tokenofglory";

    public TokenOfGlory()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}

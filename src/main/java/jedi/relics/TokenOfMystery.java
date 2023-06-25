package jedi.relics;

public class TokenOfMystery
    extends AbstractJediRelic
{
    public static final String ID = "jedi:tokenofmystery";

    public TokenOfMystery()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}

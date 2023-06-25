package jedi.relics;

// If you wonder how this works, go check paper patches in patches folder.

public class PaperFaux
    extends AbstractJediRelic
{
    public static final String ID = "jedi:paperfaux";

    public PaperFaux() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }
}

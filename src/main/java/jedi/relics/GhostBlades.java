package jedi.relics;

import com.megacrit.cardcrawl.cards.tempCards.Shiv;

public class GhostBlades
    extends AbstractJediRelic
{
    public static final String ID = "jedi:ghostblades";

    public GhostBlades()
    {
        super(ID, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.description = DESCRIPTIONS[0];
        cardToPreview = new Shiv();
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}

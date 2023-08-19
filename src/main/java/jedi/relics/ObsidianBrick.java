package jedi.relics;

import jedi.jedi;
import jedi.util.Wiz;

public class ObsidianBrick extends AbstractJediRelic
{
    public static final String ID = jedi.makeID(ObsidianBrick.class.getSimpleName());
    private static final int threshold = 10;
    public ObsidianBrick() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public String getUpdatedDescription() {
        return String.format(DESCRIPTIONS[0], threshold);
    }

    @Override
    public void onVictory() {
        Wiz.adp().gainGold(Wiz.adp().currentHealth / threshold);
    }
}

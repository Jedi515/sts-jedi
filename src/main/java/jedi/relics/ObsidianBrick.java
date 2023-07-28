package jedi.relics;

import jedi.jedi;
import jedi.util.Wiz;

public class ObsidianBrick extends AbstractJediRelic
{
    public static final String ID = jedi.makeID(ObsidianBrick.class.getSimpleName());
    public ObsidianBrick() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public void onVictory() {
        Wiz.adp().gainGold(Wiz.adp().currentHealth / 10);
    }
}

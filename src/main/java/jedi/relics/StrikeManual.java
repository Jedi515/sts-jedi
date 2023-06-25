package jedi.relics;

import jedi.actions.CustomDiscoveryAction;
import jedi.jedi;

public class StrikeManual
    extends AbstractJediRelic
{
    public static final String ID = "jedi:strikemanual";

    public StrikeManual()
    {
        super(ID, RelicTier.DEPRECATED, LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onVictory() {
        usedUp = false;
    }

    @Override
    public void atBattleStart() {
        addToBot(new CustomDiscoveryAction(jedi.StrikeGroup));
    }
}

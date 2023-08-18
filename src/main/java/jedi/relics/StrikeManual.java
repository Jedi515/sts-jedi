package jedi.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import jedi.actions.StrikeManualAction;

public class StrikeManual
    extends AbstractJediRelic
{
    public static final String ID = "jedi:strikemanual";

    public StrikeManual()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT);
    }

    public void onPlayerEndTurn() {
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new StrikeManualAction());
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}

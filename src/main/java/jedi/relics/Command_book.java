package jedi.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

public class Command_book
    extends AbstractCommand
{
    public static final String ID = "jedi:command_book";
    public ArrayList<AbstractRelic> relics;

    public Command_book()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
        relics = new ArrayList<>();
    }

    protected void openRelicSelect() {
        relics.forEach(r-> {
            UnlockTracker.markRelicAsSeen(r.relicId);
            r.isSeen = true;
        });
        openRelicSelect(relics);
    }
}

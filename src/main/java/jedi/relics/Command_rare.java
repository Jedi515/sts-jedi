package jedi.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import jedi.jedi;

import java.util.ArrayList;

public class Command_rare
    extends AbstractCommand
{
    public static final String ID = "jedi:command_rare";

    public Command_rare()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    protected void openRelicSelect() {
        this.relicSelected = false;
        ArrayList<AbstractRelic> relics = new ArrayList<>(RelicLibrary.rareList);
        if (!jedi.CommandHasCopy)
        {
            relics.removeIf((r) -> AbstractDungeon.player.hasRelic(r.relicId));
        }

        if (!jedi.CommandLocked)
        {
            relics.removeIf((r) -> UnlockTracker.isRelicLocked(r.relicId));
        }
        else
        {
            jedi.lockedRelics.clear();
            for (AbstractRelic r : relics)
            {
                if (UnlockTracker.isRelicLocked(r.relicId))
                {
                    jedi.lockedRelics.add(r.relicId);
                    UnlockTracker.lockedRelics.remove(r.relicId);
                }
            }
        }

        if (!jedi.CommandUnseen)
        {
            relics.removeIf((r) -> !UnlockTracker.isRelicSeen(r.relicId));
        }
        else
        {
            jedi.unseenRelics.clear();
            for (AbstractRelic r : relics)
            {
                if (!UnlockTracker.isRelicSeen(r.relicId))
                {
                    jedi.unseenRelics.add(r);
                    r.isSeen = true;
                }
            }
        }
        this.relicSelectScreen.open(relics);
    }


    public int getPrice()
    {
        tier = RelicTier.RARE;
        int price = super.getPrice();
        tier = RelicTier.SPECIAL;
        return price;
    }
}

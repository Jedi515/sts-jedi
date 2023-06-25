package jedi.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import jedi.jedi;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Command_book
    extends AbstractCommand
{
    public static final String ID = "jedi:command_book";
    public ArrayList<AbstractRelic> relics = new ArrayList<>();

    public Command_book()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    protected void openRelicSelect() {
        relicSelected = false;

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
            jedi.unseenRelics.addAll(relics.stream().filter(r -> !UnlockTracker.isRelicSeen(r.relicId)).collect(Collectors.toList()));
            relics.removeAll(jedi.unseenRelics);
            for (AbstractRelic r : jedi.unseenRelics)
            {
                UnlockTracker.markRelicAsSeen(r.relicId);
                r.isSeen = true;
                relics.add(RelicLibrary.getRelic(r.relicId).makeCopy());
            }
            relics.forEach(r -> r.isSeen = true);
        }
        this.relicSelectScreen.open(relics);
    }
}

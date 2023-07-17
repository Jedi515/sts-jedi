package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import jedi.relics.OminousLoanNote;

@SpirePatch(clz = AbstractPlayer.class, method = "gainGold")
public class OminousNotePatch
{
    public static void Prefix(AbstractPlayer __instance, @ByRef int[] amount)
    {
        if (AbstractDungeon.player.hasRelic(Ectoplasm.ID)) return;
        OminousLoanNote ominousLoanNote = (OminousLoanNote) AbstractDungeon.player.getRelic(OminousLoanNote.ID);
        if (ominousLoanNote == null) return;
        if (ominousLoanNote.usedUp) return;

        if (ominousLoanNote.counter <= amount[0])
        {
            amount[0] -= ominousLoanNote.counter;
            ominousLoanNote.counter = 0;
            ominousLoanNote.usedUp = true;
        }
        else
        {
            ominousLoanNote.counter -= amount[0];
            amount[0] = 0;
            System.out.println("Money");
        }
    }
}

package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import mod.jedi.relics.OminousLoanNote;

@SpirePatch(clz = AbstractPlayer.class, method = "gainGold")
public class OminousNotePatch
{
    public static void Prefix(AbstractPlayer __instance, @ByRef int[] amount)
    {
        OminousLoanNote ominousLoanNote = (OminousLoanNote) AbstractDungeon.player.getRelic(OminousLoanNote.ID);
        if (ominousLoanNote != null && !AbstractDungeon.player.hasRelic(Ectoplasm.ID))
        {
            if (!ominousLoanNote.paidOff)
                if (ominousLoanNote.counter <= amount[0])
                {
                    amount[0] -= ominousLoanNote.counter;
                    ominousLoanNote.counter = 0;
                    ominousLoanNote.paidOff = true;
                }
                else
                {
                    ominousLoanNote.counter -= amount[0];
                    amount[0] = 0;
                }
        }
    }
}

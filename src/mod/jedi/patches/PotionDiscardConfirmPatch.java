package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class PotionDiscardConfirmPatch
{
    @SpirePatch(clz = PotionPopUp.class, method = SpirePatch.CLASS)
    public static class AddField
    {
        public static SpireField<Boolean> firstPressed = new SpireField<>(() -> true);
    }

    @SpirePatch(clz = PotionPopUp.class, method = "updateInput")
    public static class ConfirmDiscard
    {
        @SpireInsertPatch(locator = Locator.class)
        public static SpireReturn Insert(PotionPopUp __instance)
        {
            if (AddField.firstPressed.get(__instance))
            {
                AddField.firstPressed.set(__instance, false);
                CardCrawlGame.sound.play("jedi:RUSure");
                return SpireReturn.Return(null);
            }
            AddField.firstPressed.set(__instance, true);
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = PotionPopUp.class, method = "close")
    public static class CloseReset
    {
        public static void Postfix(PotionPopUp __instance)
        {
            AddField.firstPressed.set(__instance, true);
        }
    }

    private static class Locator extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(SoundMaster.class, "play");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}

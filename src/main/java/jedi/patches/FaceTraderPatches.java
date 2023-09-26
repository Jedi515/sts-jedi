package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.shrines.FaceTrader;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import jedi.relics.Command_face;

import java.util.ArrayList;

public class FaceTraderPatches {

    @SpirePatch2(clz = FaceTrader.class, method = "getRandomFace")
    public static class GetFacePatch
    {

        public static ExprEditor Instrument()
        {
            return new ExprEditor()
            {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getMethodName().equals("makeCopy"))
                    {
                        m.replace("$_ = " + GetFacePatch.class.getName() + ".getCommandRelic($proceed(), ids);");
                    }
                }
            };
        }

        public static AbstractRelic getCommandRelic(AbstractRelic __result, ArrayList<String> faces)
        {
            if (jedi.jedi.isCommandRun()) {
                Command_face f = new Command_face();
                faces.forEach(str -> {
                    UnlockTracker.markRelicAsSeen(str);
                    f.relics.add(RelicLibrary.getRelic(str).makeCopy());
                });
                return f;
            }
            return __result;
        }
    }
    @SpirePatch2(clz = FaceTrader.class, method = "buttonEffect")
    public static class DescriptionPatch
    {
        private static boolean firstLoop = true;
        public static ExprEditor Instrument()
        {
            return new ExprEditor()
            {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (firstLoop && m.getMethodName().equals("setDialogOption"))
                    {
                        firstLoop = false;
                        m.replace("$1 = " + DescriptionPatch.class.getName() + ".getDescription($1); $proceed($$);");
                    }
                }
            };
        }

        public static String getDescription(String origDesc)
        {
            if (jedi.jedi.isCommandRun())
                return CardCrawlGame.languagePack.getRelicStrings("jedi:command_face").DESCRIPTIONS[1];
            return origDesc;
        }
    }
}

package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.events.shrines.FaceTrader;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import jedi.relics.Command_face;

import java.util.ArrayList;

@SpirePatch(clz = FaceTrader.class, method = "getRandomFace")
public class FaceTraderPatch {

    public static ExprEditor Instrument()
    {
        return new ExprEditor()
        {
            @Override
            public void edit(MethodCall m) throws CannotCompileException {
                if (m.getMethodName().equals("makeCopy"))
                {
                    m.replace("$_ = " + FaceTraderPatch.class.getName() + ".getCommandRelic($proceed(), ids);");
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

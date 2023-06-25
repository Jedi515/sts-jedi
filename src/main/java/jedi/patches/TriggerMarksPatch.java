package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.powers.watcher.MarkPower;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(clz = MarkPower.class, method = "triggerMarks")
public class TriggerMarksPatch
{
    public static ExprEditor Instrument()
    {
        return new ExprEditor()
        {
            @Override
            public void edit(MethodCall m) throws CannotCompileException
            {
                if (m.getClassName().equals("java.lang.String") && m.getMethodName().equals("equals"))
                {
                    m.replace("{$_ = true;}");
                }
            }
        };
    }
}

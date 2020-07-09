package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import mod.jedi.interfaces.OnBeingScriedInterface;

@SpirePatch(clz = ScryAction.class, method = "update")
public class OnBeingScriedPatch
{
    public static ExprEditor Instrument()
    {
        return new ExprEditor()
        {
            @Override
            public void edit(MethodCall m) throws CannotCompileException
            {
                if (m.getMethodName().equals("moveToDiscardPile"))
                {
                    m.replace("$proceed($$);" +
                            "if ($1 instanceof "+ OnBeingScriedInterface.class.getName() + ") ((" + OnBeingScriedInterface.class.getName() + ")$1).onBeingScried();");
                }
            }
        };
    }
}

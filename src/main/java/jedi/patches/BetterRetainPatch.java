package jedi.patches;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.powers.RetainCardPower;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.NewExpr;

@SpirePatch2(clz = RetainCardPower.class, method = "atEndOfTurn")
public class BetterRetainPatch
{
    public static ExprEditor Instrument()
    {
        return new ExprEditor()
        {
            @Override
            public void edit(NewExpr e) throws CannotCompileException
            {
                if (e.getClassName().equals(RetainCardsAction.class.getName()))
                {
                    e.replace("$_ = " + BetterRetainPatch.class.getName() + ".getBetterRetainPower(amount);");
                }
            }
        };
    }

    public static AbstractGameAction getBetterRetainPower(int amount)
    {
        return new SelectCardsInHandAction(amount, RetainCardsAction.TEXT[0], true, true,
                c -> (!c.isEthereal && !c.selfRetain && !c.retain),
                list -> list.forEach(c -> c.retain = true));
    }
}

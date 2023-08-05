package jedi.util;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.megacrit.cardcrawl.cards.AbstractCard;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

public class ClassScanner {
    public static boolean checkIfContainsWords(AbstractCard card, String[] words)
    {
        for (String s : words)
        {
            if (checkIfContainsWord(card, s)) return true;
        }
        return false;
    }

    public static boolean checkIfContainsWord(AbstractCard card, String word)
    {
        boolean toReturn = false;
        if (card.cardID.toLowerCase().contains(word)) return true;
        for (String kw : card.keywords)
        {
            if (kw.toLowerCase().equals(word)) return true;
        }
        if (card.getClass().getName().toLowerCase().contains(word)) return true;
        try {
            CtClass ctClass = Loader.getClassPool().get(card.getClass().getName());
            ctClass.defrost();

            String[] methods = {"use", "triggerOnManualDiscard", "triggerWhenDrawn", "calculateCardDamage", "triggerOnExhaust"};

            toReturn = ifFunctionsContainWord(ctClass, methods, word);
            ctClass.freeze();
        } catch (NotFoundException ignored)
        {

        }
        return toReturn;
    }

    public static boolean ifFunctionsContainWord(CtClass ctClass, String[] methods, String word)
    {
        for (String s : methods)
        {
            if (ifFunctionContainsWord(ctClass, s, word)) return true;
        }

        return false;
    }

    public static boolean ifFunctionContainsWord(CtClass ctClass, String method, String word)
    {
        final boolean[] toReturn = {false};
        try
        {

            CtMethod ctMethod = ctClass.getDeclaredMethod(method);

            ExprEditor wordNewExprFinder = new ExprEditor(){
                @Override
                public void edit(NewExpr e)
                {
                    if (e.getClassName().toLowerCase().contains(word))
                    {
                        toReturn[0] = true;
                    }
                }
            };

            ExprEditor wordMethodCallFinder = new ExprEditor(){
                @Override
                public void edit(MethodCall m)
                {
                    if (m.getClassName().toLowerCase().contains(word))
                    {
                        toReturn[0] = true;
                    }
                }
            };

            ExprEditor wordFieldAccessFinder = new ExprEditor(){
                @Override
                public void edit(FieldAccess f)
                {
                    if (f.getClassName().toLowerCase().contains(word))
                    {
                        toReturn[0] = true;
                    }
                }
            };

            ctMethod.instrument(wordNewExprFinder);
            if (!toReturn[0]) ctMethod.instrument(wordFieldAccessFinder);
            if (!toReturn[0]) ctMethod.instrument(wordMethodCallFinder);

        } catch (NotFoundException | CannotCompileException e) {
        }
        return toReturn[0];
    }
}

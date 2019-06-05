package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.BloodPotion;
import com.megacrit.cardcrawl.potions.EntropicBrew;
import com.megacrit.cardcrawl.potions.FruitJuice;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.Instanceof;
import mod.jedi.interfaces.OutOfCombatPotion;

public class OuftOfCombatPotionPatch
{
    @SpirePatch(clz = PotionPopUp.class, method = "updateInput")
    @SpirePatch(clz = PotionPopUp.class, method = "render")
    public static class RenderUsable
    {
        public static ExprEditor Instrument()
        {
            return new ExprEditor() {
                @Override
                public void edit(Instanceof i) throws CannotCompileException
                {
                    try
                    {
                        System.out.println("JEDI MOD: " + i.getType() + " " + i.getType().getName().equals(FruitJuice.class.getName()));
                        if (i.getType().getName().equals(FruitJuice.class.getName()))
                        {
                            i.replace("{$_ = $1 instanceof " + OutOfCombatPotion.class.getName() + ";}");
                        }
                    }
                    catch (NotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
            };
        }
    }

//    @SpirePatch(clz = PotionPopUp.class, method = "render")
//    public static class RenderConditionallyUsable
//    {
//        public static ExprEditor Instrument()
//        {
//            return new ExprEditor() {
//                @Override
//                public void edit(Instanceof i) throws CannotCompileException
//                {
//                    try
//                    {
//                        System.out.println("JEDI MOD: " + i.getType() + " " + i.getType().getName().equals(FruitJuice.class.getName()));
//                        if (i.getType().getName().equals(FruitJuice.class.getName()))
//                        {
//                            i.replace("{$_ = ((" + AbstractPotion.class.getName() + ")$1).canUse();}");
//                        }
//                    }
//                    catch (NotFoundException e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//            };
//        }
//    }


    @SpirePatch(clz = FruitJuice.class, method = SpirePatch.CONSTRUCTOR)
    @SpirePatch(clz = BloodPotion.class, method = SpirePatch.CONSTRUCTOR)
    @SpirePatch(clz = EntropicBrew.class, method = SpirePatch.CONSTRUCTOR)
    public static class AddToExistingPotions
    {
        public static void Raw(CtBehavior ctMethodToPatch) throws NotFoundException, CannotCompileException
        {
            CtClass ctClass = ctMethodToPatch.getDeclaringClass();
            ClassPool pool = ctClass.getClassPool();

            CtClass OOPInterface = pool.get(OutOfCombatPotion.class.getName());
            ctClass.addInterface(OOPInterface);
        }
    }

    @SpirePatch(clz = AbstractPotion.class, method = "canUse")
    public static class MakeActuallyUsable
    {
        public static boolean Postfix(boolean __result, AbstractPotion __instance)
        {
            if (AbstractDungeon.getCurrRoom().event != null && AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain)
            {
                return __result;
            }
            return (__result || (__instance instanceof OutOfCombatPotion));
        }
    }
}

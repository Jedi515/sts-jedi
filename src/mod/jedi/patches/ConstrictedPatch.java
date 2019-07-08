package mod.jedi.patches;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.powers.ConstrictedPower;
import javassist.*;

@SpirePatch(clz = ConstrictedPower.class, method = SpirePatch.CONSTRUCTOR)
public class ConstrictedPatch
{
    public static void Raw(CtBehavior ctMethodToPatch) throws NotFoundException, CannotCompileException
    {
        CtClass ctClass = ctMethodToPatch.getDeclaringClass();
        ClassPool pool = ctClass.getClassPool();

        CtClass hpRenderPowerClass = pool.get(HealthBarRenderPower.class.getName());
        ctClass.addInterface(hpRenderPowerClass);

        CtClass ctColor = pool.get(Color.class.getName());

        //Color
        ctClass.addMethod(CtNewMethod.make(
                ctColor,
                "getColor",
                new CtClass[]{},
                null,
                "return " + Color.class.getName() + ".BLUE;",
                ctClass));

        //Amount
        ctClass.addMethod(CtNewMethod.make(
                "public int getHealthBarAmount() {" +
                        "if (this.owner.currentBlock > this.amount) {return 0;}" +
                        "return (this.amount - this.owner.currentBlock);" +
                        "}",
                ctClass
        ));
    }
}

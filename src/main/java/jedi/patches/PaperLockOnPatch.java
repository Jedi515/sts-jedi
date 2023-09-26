package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CannotCompileException;
import javassist.CodeConverter;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.bytecode.*;
import javassist.convert.Transformer;
import jedi.relics.PaperFaux;
import jedi.util.Wiz;

@SpirePatch2(clz= AbstractOrb.class, method = "applyLockOn")
@SpirePatch2(
        clz = DamageInfo.class,
        method = "createDamageMatrix",
        paramtypez = {int.class, boolean.class, boolean.class})
public class PaperLockOnPatch
{
    public static float multiplyLockOn(float baseMul)
    {
        return baseMul + PaperFaux.dmgMod * Wiz.adp().relics.stream().filter(r -> r.relicId.equals(PaperFaux.ID)).count();
    }

    public static class TransformBipush extends Transformer
    {
        protected CodeAttribute codeAttr;

        public TransformBipush(Transformer next)
        {
            super(next);
        }

        @Override
        public void initialize(ConstPool cp, CodeAttribute attr)
        {
            codeAttr = attr;
        }

        @Override
        public int transform(CtClass clazz, int pos, CodeIterator iterator,
                             ConstPool cp) throws BadBytecode
        {
            int c = iterator.byteAt(pos);
            if (c == LDC) {
                int v = iterator.byteAt(pos + 2);
                if (v == FMUL)
                {
                    Bytecode bc = new Bytecode(cp);
                    CtClass[] args = new CtClass[1];
                    args[0] = CtClass.floatType;

                    bc.addInvokestatic(PaperLockOnPatch.class.getName(), "multiplyLockOn", Descriptor.ofMethod(CtClass.floatType, args));
                    iterator.insert(pos+2, bc.get());
                }
            }
            return pos;
        }
    }

    public static class TestCodeConverter extends CodeConverter
    {
        public void bipush()
        {
            transformers = new TransformBipush(transformers);
        }
    }

    public static void Raw(CtBehavior ctMethodToPatch) throws CannotCompileException
    {
        TestCodeConverter codeConverter = new TestCodeConverter();
        codeConverter.bipush();
        ctMethodToPatch.instrument(codeConverter);
    }
}
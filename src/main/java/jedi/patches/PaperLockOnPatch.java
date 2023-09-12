package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import jedi.relics.PaperFaux;
import jedi.util.Wiz;

public class PaperLockOnPatch
{
    public static int multiplyLockOn(AbstractCreature target, int baseDmg, int dmg)
    {
        return (int)((float)baseDmg * (1.5F + PaperFaux.dmgMod * Wiz.adp().relics.stream().filter(r -> r.relicId.equals(PaperFaux.ID)).count()));

    }

    @SpirePatch(clz= AbstractOrb.class, method = "applyLockOn")
    public static class SingleTarget {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    if (!f.isWriter() || !f.getFieldName().equals("retVal")) return;

                    f.replace("$1 = " + PaperLockOnPatch.class.getName()
                            + ".multiplyLockOn(target, dmg, $1);" +
                            "$proceed($$);");

                }
            };
        }
    }
    @SpirePatch(
            clz = DamageInfo.class,
            method = "createDamageMatrix",
            paramtypez = {int.class, boolean.class, boolean.class})
    public static class ElectroTarget
    {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(FieldAccess f) throws CannotCompileException {
                    if (!f.isWriter() || !f.getFieldName().equals("output")) return;

                    f.replace("$1 = " + PaperLockOnPatch.class.getName()
                            + ".multiplyLockOn((" + AbstractMonster.class.getName() + ")"
                            + AbstractDungeon.class.getName()
                            + ".getMonsters().monsters.get(i), info.base, $1);" +
                            "$proceed($$);");

                }
            };
        }
    }
}

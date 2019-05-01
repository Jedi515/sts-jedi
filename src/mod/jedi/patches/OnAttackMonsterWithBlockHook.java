package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.jedi;

@SpirePatch(clz = AbstractMonster.class, method = "damage")
public class OnAttackMonsterWithBlockHook
{
    public static void Prefix(AbstractMonster __instance, DamageInfo info)
    {
        info.output = jedi.publishAttackMonsterChange(info, info.output);
    }
}

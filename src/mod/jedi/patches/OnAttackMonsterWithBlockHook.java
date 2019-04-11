package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@SpirePatch(clz = AbstractMonster.class, method = "damage")
public class OnAttackMonsterWithBlockHook
{
    public static void Prefix(AbstractMonster __instance, DamageInfo info)
    {
        info.output = sts_jedi.jedi.publishAttackMonsterChange(info, info.output);
    }
}

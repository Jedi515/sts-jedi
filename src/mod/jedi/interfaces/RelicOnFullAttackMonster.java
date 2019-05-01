package mod.jedi.interfaces;

import com.megacrit.cardcrawl.cards.DamageInfo;

public interface RelicOnFullAttackMonster
{
    default int betterOnAttackedMonster(DamageInfo info, int damageAmount)
    {
        return damageAmount;
    }
}

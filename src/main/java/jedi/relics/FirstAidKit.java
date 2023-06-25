package jedi.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RegenPower;

public class FirstAidKit
    extends AbstractJediRelic
{

    public static final String ID = "jedi:firstaidkit";

    public FirstAidKit()
    {
        super(ID, RelicTier.RARE, LandingSound.FLAT);
    }


    public int onAttacked(DamageInfo info, int damageAmount)
    {
        AbstractPlayer p = AbstractDungeon.player;
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != p && damageAmount > 0 && !p.hasPower("Buffer"))
        {
            addToBot(new ApplyPowerAction(p, p, new RegenPower(p, 2), 2));
        }
        return damageAmount;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

}

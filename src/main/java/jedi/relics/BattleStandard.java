package jedi.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BattleStandard
    extends AbstractJediRelic
{
    public static final String ID = "jedi:battlestandard";

    public BattleStandard()
    {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    //Of course, it's a patch. What did you expect? Patches/BattleStandardPatch

    public void onEquip()
    {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    public void onUnequip()
    {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }
}

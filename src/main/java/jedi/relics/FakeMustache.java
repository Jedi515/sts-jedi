package jedi.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FakeMustache
    extends AbstractJediRelic
{
    public static final String ID = "jedi:fakemustache";

    public FakeMustache() {
        super(ID, RelicTier.SHOP,LandingSound.FLAT);
    }

    public void onSpendGold()
    {
        AbstractDungeon.player.heal(5);
    }

    public void onEquip()
    {
        AbstractDungeon.player.heal(10);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }
}

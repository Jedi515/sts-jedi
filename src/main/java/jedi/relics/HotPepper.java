package jedi.relics;


import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class HotPepper
    extends AbstractJediRelic
{
    public static final String ID = "jedi:hotpepper";
    private static float decrease_flat = 0.3F;

    public HotPepper() {
        super(ID, AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public void onEquip()
    {
        AbstractDungeon.player.energy.energyMaster += 1;
        AbstractDungeon.player.decreaseMaxHealth((int)Math.ceil(AbstractDungeon.player.maxHealth * decrease_flat));
    }

    public void onUnequip()
    {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    public AbstractRelic makeCopy()
    {
        return new HotPepper();
    }
}

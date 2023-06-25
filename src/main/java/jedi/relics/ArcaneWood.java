package jedi.relics;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ArcaneWood
    extends AbstractJediRelic
{
    public static final String ID = "jedi:arcanewood";

    public ArcaneWood()
    {
        super(ID, RelicTier.COMMON, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        counter = -2;
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }
}

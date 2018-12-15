package mod.jedi.relics;


import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class HotPepper
    extends CustomRelic
{
    public static final String ID = "jedi:hotpepper";
    private static float decrease_flat = 0.35F;
    private static float decrease_multiplier = 0.5F;

    public HotPepper() {
        super("jedi:hotpepper", new Texture("resources/images/relics/hotpepper.png"), AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
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

    public int onMaxHPChange(int amount)
    {
        if (amount > 0)
        {
            amount *= (1 - decrease_multiplier);
            flash();
        }
        return (int)(amount);
    }
}
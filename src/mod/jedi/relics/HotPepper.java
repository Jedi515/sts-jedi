package mod.jedi.relics;


import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mod.jedi.util.TextureLoader;

public class HotPepper
    extends CustomRelic
{
    public static final String ID = "jedi:hotpepper";
    private static float decrease_flat = 0.35F;
    private static float decrease_multiplier = 0.5F;
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);

    public HotPepper() {
        super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
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

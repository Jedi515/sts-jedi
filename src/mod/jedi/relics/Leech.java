package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.helpers.PowerTip;
import mod.jedi.util.TextureLoader;

public class Leech
    extends CustomRelic
    implements BetterOnLoseHpRelic
{
    public static final String ID = "jedi:leech";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);

    public Leech()
    {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    @Override
    public int betterOnLoseHp(DamageInfo damageInfo, int i)
    {
        if (damageInfo.type == DamageInfo.DamageType.HP_LOSS && i > 1)
        {
            return i-1;
        }
        else
        {
            return i;
        }
    }

    public CustomRelic makeCopy()
    {
        return new Leech();
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }
}

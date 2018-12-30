package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.BetterOnLoseHpRelic;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class Leech
    extends CustomRelic
    implements BetterOnLoseHpRelic
{
    public static final String ID = "jedi:leech";
    public static final String IMG_PATH = "resources/jedi/images/relics/beta_rock.png";

    public Leech()
    {
        super(ID, new Texture(IMG_PATH), RelicTier.UNCOMMON, LandingSound.FLAT);
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

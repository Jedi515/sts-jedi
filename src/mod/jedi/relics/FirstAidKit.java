package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RegenPower;
import mod.jedi.util.TextureLoader;

public class FirstAidKit
    extends CustomRelic
{

    public static final String ID = "jedi:firstaidkit";
    public static final String IMG_PATH = "resources/jedi/images/relics/firstaidkit.png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);

    public FirstAidKit()
    {
        super(ID, IMG, RelicTier.RARE, LandingSound.FLAT);
    }


    public int onAttacked(DamageInfo info, int damageAmount)
    {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != AbstractDungeon.player && damageAmount > 0 && !AbstractDungeon.player.hasPower("Buffer"))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RegenPower(AbstractDungeon.player, 2), 2));
        }
        return damageAmount;
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }

    public CustomRelic makeCopy()
    {
        return new FirstAidKit();
    }
}

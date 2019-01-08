package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import mod.jedi.util.TextureLoader;

public class HeavyJacket
    extends CustomRelic
{

    public static final String ID = "jedi:heavyjacket";
    public static final String IMG_PATH = "resources/jedi/images/relics/heavyjacket.png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);

    public HeavyJacket()
    {
        super(ID, IMG, RelicTier.UNCOMMON, LandingSound.FLAT);
    }

    public void atBattleStart()
    {
        int PA = AbstractDungeon.player.relics.size() / 2;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, PA), PA));
    }

    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0];
    }
}

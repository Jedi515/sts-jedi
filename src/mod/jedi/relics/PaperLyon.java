package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.OddMushroom;
import mod.jedi.util.TextureLoader;

public class PaperLyon
    extends CustomRelic
{
    public static final String ID = "jedi:paperlyon";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    public static final OddMushroom shroom = new OddMushroom();

    public PaperLyon() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
        this.description = DESCRIPTIONS[0] + FontHelper.colorString(shroom.name, "y");
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(DESCRIPTIONS[1], DESCRIPTIONS[2] + FontHelper.colorString(shroom.name, "y") + DESCRIPTIONS[3]));
        this.initializeTips();

    }

    //Make a wild guess. It's a patch. patches/PaperLyonPatch

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
        return this.DESCRIPTIONS[0];
    }
}

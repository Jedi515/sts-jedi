package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mod.jedi.actions.ShrinkRayAction;
import mod.jedi.util.TextureLoader;

//guess what, another patch on size
public class ShrinkRay
    extends CustomRelic
{
    public static final String ID = "jedi:shrinkray";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    private float MODIFIER_AMT = 0.1F;

    public ShrinkRay() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
    }

    public void atBattleStart() {
            this.flash();
            AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToTop(new ShrinkRayAction(MODIFIER_AMT));
    }


    public String getUpdatedDescription()
    {
        return this.DESCRIPTIONS[0] + 10 + this.DESCRIPTIONS[1];
    }
}

package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mod.jedi.util.TextureLoader;

public class AngryMask
    extends CustomRelic
{
    public static final String ID = "jedi:angrymask";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    private static final int strGain = 2;

    public AngryMask()
    {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    public void onEquip()
    {
        this.counter = -1;
    }

    public void onSpendGold()
    {
        this.counter = -2;
        this.beginLongPulse();
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48;
    }

    public void atBattleStart()
    {
        if (this.counter == -2)
        {
            AbstractPlayer p = AbstractDungeon.player;
            this.stopPulse();
            this.counter = -1;
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, strGain), strGain));
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + strGain + DESCRIPTIONS[1];
    }
}

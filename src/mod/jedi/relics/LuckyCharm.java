package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import mod.jedi.jedi;
import mod.jedi.util.TextureLoader;

public class LuckyCharm
    extends CustomRelic
{
    public static final String ID = "jedi:luckycharm";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    private static int cursedCounter = 0;
    private static int effectiveness = 1;

    public LuckyCharm()
    {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public void onEquip()
    {
        cursedCounter = 1;
        for (AbstractRelic r : AbstractDungeon.player.relics)
        {
            if (jedi.cursedRelics.contains(r.relicId))
            {
                cursedCounter++;
            }
        }
        this.counter = cursedCounter;
    }

    @Override
    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0] + effectiveness + DESCRIPTIONS[1];
    }

    public void atBattleStart()
    {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.counter * effectiveness), this.counter * effectiveness   ));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, this.counter * effectiveness), this.counter * effectiveness));
    }

    public void modifyCounter(String relicToGet)
    {
        cursedCounter = 0;
        if (jedi.cursedRelics.contains(relicToGet)) cursedCounter++;
        for (AbstractRelic r : AbstractDungeon.player.relics)
        {
            if (jedi.cursedRelics.contains(r.relicId))
            {
                cursedCounter++;
            }
        }
        this.counter = cursedCounter;
    }

    public int getPrice()
    {
        return 0;
    }
}

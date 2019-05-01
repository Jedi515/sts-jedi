package mod.jedi.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.hubris.relics.CrackedHourglass;
import com.evacipated.cardcrawl.mod.hubris.relics.Metronome;
import com.evacipated.cardcrawl.mod.hubris.relics.Stopwatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.MercuryHourglass;
import com.megacrit.cardcrawl.relics.Pocketwatch;
import com.megacrit.cardcrawl.relics.StoneCalendar;
import com.megacrit.cardcrawl.relics.Sundial;
import mod.jedi.util.TextureLoader;
import mod.jedi.jedi;

public class WindUpBox
    extends CustomRelic
{
    public static final String ID = "jedi:windupbox";
    public static final String PATH = "resources/jedi/images/relics/";
    public static final String OUTLINE_PATH = PATH + "outline/" + ID.substring(5) + ".png";
    public static final String IMG_PATH = PATH + ID.substring(5) + ".png";
    private static final Texture IMG = TextureLoader.getTexture(IMG_PATH);
    private static final Texture OUTLINE = TextureLoader.getTexture(OUTLINE_PATH);
    private static MercuryHourglass mercuryHourglass = new MercuryHourglass();
    private static Pocketwatch pocketwatch = new Pocketwatch();
    private static StoneCalendar stoneCalendar = new StoneCalendar();
    private static Sundial sundial = new Sundial();

    public WindUpBox()
    {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(mercuryHourglass.name, DESCRIPTIONS[1]));
        this.tips.add(new PowerTip(pocketwatch.name, DESCRIPTIONS[2]));
        this.tips.add(new PowerTip(stoneCalendar.name, DESCRIPTIONS[3]));
        this.tips.add(new PowerTip(sundial.name, DESCRIPTIONS[4]));
        if(jedi.isHubrisLoaded)
        {
            this.tips.add(new PowerTip(new CrackedHourglass().name, DESCRIPTIONS[5]));
            this.tips.add(new PowerTip(new Stopwatch().name, DESCRIPTIONS[6]));
            this.tips.add(new PowerTip(new Metronome().name, DESCRIPTIONS[7]));
        }
        this.initializeTips();
    }

    //By the power of patches!

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip()
    {
        if(jedi.isHubrisLoaded)
        {
            if(AbstractDungeon.player.hasRelic(Stopwatch.ID))
            {
                AbstractDungeon.player.getRelic(Stopwatch.ID).counter += 2;
            }
        }

//        this.counter = 0;
    }

//    public void onPlayerEndTurn()
//    {
//        this.counter++;
//    }
}

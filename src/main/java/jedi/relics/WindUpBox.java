package jedi.relics;

import com.evacipated.cardcrawl.mod.hubris.relics.CrackedHourglass;
import com.evacipated.cardcrawl.mod.hubris.relics.Metronome;
import com.evacipated.cardcrawl.mod.hubris.relics.Stopwatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.MercuryHourglass;
import com.megacrit.cardcrawl.relics.Pocketwatch;
import com.megacrit.cardcrawl.relics.StoneCalendar;
import com.megacrit.cardcrawl.relics.Sundial;
import jedi.jedi;

import java.util.*;

public class WindUpBox
    extends AbstractJediRelic
{
    public static final String ID = "jedi:windupbox";
    private static MercuryHourglass mercuryHourglass = new MercuryHourglass();
    private static Pocketwatch pocketwatch = new Pocketwatch();
    private static StoneCalendar stoneCalendar = new StoneCalendar();
    private static Sundial sundial = new Sundial();
    public static ArrayList<String> relicList = new ArrayList<>();

    public WindUpBox()
    {
        super(ID, RelicTier.SHOP, LandingSound.MAGICAL);
        relicList.clear();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.tips.add(new PowerTip(mercuryHourglass.name, DESCRIPTIONS[1]));
        this.tips.add(new PowerTip(pocketwatch.name, DESCRIPTIONS[2]));
        this.tips.add(new PowerTip(stoneCalendar.name, DESCRIPTIONS[3]));
        this.tips.add(new PowerTip(sundial.name, DESCRIPTIONS[4]));
        relicList.add(mercuryHourglass.relicId);
        relicList.add(pocketwatch.relicId);
        relicList.add(stoneCalendar.relicId);
        relicList.add(sundial.relicId);
        if(jedi.isHubrisLoaded)
        {
            this.tips.add(new PowerTip(new CrackedHourglass().name, DESCRIPTIONS[5]));
            relicList.add(CrackedHourglass.ID);
            this.tips.add(new PowerTip(new Stopwatch().name, DESCRIPTIONS[6]));
            relicList.add(Stopwatch.ID);
            this.tips.add(new PowerTip(new Metronome().name, DESCRIPTIONS[7]));
            relicList.add(Metronome.ID);
        }
        this.initializeTips();
    }

    @Override
    public boolean canSpawn()
    {
        return AbstractDungeon.player.relics.stream().anyMatch(relic -> relicList.contains(relic.relicId));
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

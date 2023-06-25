package jedi.modifiers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.localization.RunModStrings;

public class WarmongerRunMod
    extends AbstractDailyMod
{
    public static final String ID = "jedi:warmonger";
    private static final RunModStrings modStrings = CardCrawlGame.languagePack.getRunModString(ID);
    public static final String NAME = modStrings.NAME;
    public static final String DESCRIPTION = modStrings.DESCRIPTION;


    public WarmongerRunMod()
    {
        super(ID, NAME, DESCRIPTION, null, false);
    }
}

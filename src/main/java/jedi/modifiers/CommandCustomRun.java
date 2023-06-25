package jedi.modifiers;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.AbstractDailyMod;
import com.megacrit.cardcrawl.localization.RunModStrings;

public class CommandCustomRun
    extends AbstractDailyMod
{
    public static final String ID = "jedi:command";
    private static final RunModStrings modStrings = CardCrawlGame.languagePack.getRunModString(ID);
    public static final String NAME = modStrings.NAME;
    public static final String DESCRIPTION = modStrings.DESCRIPTION;

    public CommandCustomRun() {
        super(ID, NAME, DESCRIPTION, null, true);
    }
}

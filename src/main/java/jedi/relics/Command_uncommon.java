package jedi.relics;

import com.megacrit.cardcrawl.helpers.RelicLibrary;

import java.util.ArrayList;

public class Command_uncommon
    extends AbstractCommand
{
    public static final String ID = "jedi:command_uncommon";

    public Command_uncommon()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    protected void openRelicSelect() {
        openRelicSelect(new ArrayList<>(RelicLibrary.uncommonList));
    }

    public int getPrice()
    {
        tier = RelicTier.UNCOMMON;
        int price = super.getPrice();
        tier = RelicTier.SPECIAL;
        return price;
    }
}

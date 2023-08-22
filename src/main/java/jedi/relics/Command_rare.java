package jedi.relics;

import com.megacrit.cardcrawl.helpers.RelicLibrary;

import java.util.ArrayList;

public class Command_rare
    extends AbstractCommand
{
    public static final String ID = "jedi:command_rare";

    public Command_rare()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    protected void openRelicSelect() {
        openRelicSelect(new ArrayList<>(RelicLibrary.rareList));
    }


    public int getPrice()
    {
        tier = RelicTier.RARE;
        int price = super.getPrice();
        tier = RelicTier.SPECIAL;
        return price;
    }
}

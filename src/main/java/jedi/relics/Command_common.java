package jedi.relics;

import com.megacrit.cardcrawl.helpers.RelicLibrary;

import java.util.ArrayList;

public class Command_common
    extends AbstractCommand
{
    public static final String ID = "jedi:command_common";

    public Command_common()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    protected void openRelicSelect() {
        openRelicSelect(new ArrayList<>(RelicLibrary.commonList));
    }

    public int getPrice()
    {
        tier = RelicTier.COMMON;
        int price = super.getPrice();
        tier = RelicTier.SPECIAL;
        return price;
    }
}

package jedi.relics;

import com.megacrit.cardcrawl.helpers.RelicLibrary;

import java.util.ArrayList;

public class Command_boss
    extends AbstractCommand
{
    public static final String ID = "jedi:command_boss";

    public Command_boss()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    protected void openRelicSelect() {
        openRelicSelect(new ArrayList<>(RelicLibrary.bossList));
    }


    public int getPrice()
    {
        tier = RelicTier.BOSS;
        int price = super.getPrice();
        tier = RelicTier.SPECIAL;
        return price;
    }
}

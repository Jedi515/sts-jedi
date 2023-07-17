package jedi.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import jedi.jedi;

import java.util.ArrayList;

public class Command_shop
    extends AbstractCommand
{
    public static final String ID = "jedi:command_shop";

    public Command_shop()
    {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    protected void openRelicSelect() {
        openRelicSelect(new ArrayList<>(RelicLibrary.shopList));
    }

    public int getPrice()
    {
        tier = RelicTier.SHOP;
        int price = super.getPrice();
        tier = RelicTier.SPECIAL;
        return price;
    }
}

package mod.jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import mod.jedi.cards.blue.BurstDark;

import java.util.ArrayList;

@SpirePatch(clz = AbstractRoom.class, method = "endTurn")
public class BurstDarkPatch
{
    public static void Postfix(AbstractRoom __instance)
    {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(FocusPower.POWER_ID))
        {
            int focus = AbstractDungeon.player.getPower(FocusPower.POWER_ID).amount;
            for (AbstractCard c : AbstractDungeon.player.drawPile.group)
            {
                if (c instanceof BurstDark)
                {
                    c.damage += focus;
                    c.baseDamage += focus;
                }
            }
            for (AbstractCard c : AbstractDungeon.player.discardPile.group)
            {
                if (c instanceof BurstDark)
                {
                    c.damage += focus;
                    c.baseDamage += focus;
                }
            }
            for (AbstractCard c : AbstractDungeon.player.hand.group)
            {
                if (c instanceof BurstDark)
                {
                    c.damage += focus;
                    c.baseDamage += focus;
                }
            }
        }
    }
}

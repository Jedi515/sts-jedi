package jedi.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.CursedTome;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import jedi.modifiers.CommandCustomRun;
import jedi.relics.Command_book;
import jedi.relics.MainCommand;
import jedi.relics.Zontanonomicon;

import java.util.ArrayList;

@SpirePatch(clz = CursedTome.class, method = "randomBook")
public class CursedTomePatch
{
    @SpireInsertPatch(locator = LocatorLiving.class, localvars = {"possibleBooks"})
    public static void InsertBook(CursedTome __instance, @ByRef ArrayList<AbstractRelic>[] possibleBooks)
    {
        if (!AbstractDungeon.player.hasRelic(Zontanonomicon.ID)) {
            possibleBooks[0].add(RelicLibrary.getRelic(Zontanonomicon.ID).makeCopy());
        }
    }

    private static class LocatorLiving extends SpireInsertLocator
    {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "player");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }

    public static AbstractRelic insertCommand(ArrayList<AbstractRelic> possibleBooks, AbstractRelic r)
    {
        if (AbstractDungeon.player.hasRelic(MainCommand.ID) || ((CardCrawlGame.trial != null) && (CardCrawlGame.trial.dailyModIDs().contains(CommandCustomRun.ID))))
        {
            r = new Command_book();
            ((Command_book)r).relics = possibleBooks;

        }
        return r;
    }

    public static ExprEditor Instrument()
    {
        return new ExprEditor(){
            @Override
            public void edit(MethodCall m) throws CannotCompileException
            {
                if (m.getMethodName().equals("addRelicToRewards"))
                {
                    m.replace("$1 = " + CursedTomePatch.class.getName() + ".insertCommand(possibleBooks, $$); $proceed($$);");
                }
            }
        };
    }
}

package sts_jedi;

import basemod.helpers.RelicType;
import basemod.interfaces.EditCardsSubscriber;
import com.evacipated.cardcrawl.mod.jedi.cards.blue.crystallizeddark;
import com.evacipated.cardcrawl.mod.jedi.cards.blue.crystallizedfrost;
import com.evacipated.cardcrawl.mod.jedi.cards.blue.crystallizedlightning;
import com.evacipated.cardcrawl.mod.jedi.cards.colorless.Cleanse;
import com.evacipated.cardcrawl.mod.jedi.cards.colorless.Forcepull;
import com.evacipated.cardcrawl.mod.jedi.cards.colorless.Forcepush;
import com.evacipated.cardcrawl.mod.jedi.relics.HotPepper;
import com.evacipated.cardcrawl.mod.jedi.relics.Matchstick;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import basemod.BaseMod;
import basemod.interfaces.EditRelicsSubscriber;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import basemod.interfaces.EditStringsSubscriber;

import static basemod.BaseMod.loadCustomStringsFile;

@SpireInitializer
public class jedi implements EditRelicsSubscriber, EditStringsSubscriber, EditCardsSubscriber
{

    public static void initialize()
    {
        BaseMod.subscribe(new jedi());
    }

    @Override
    public void receiveEditCards()
    {
        BaseMod.addCard(new Cleanse());
        BaseMod.addCard(new Forcepush());
        BaseMod.addCard(new Forcepull());
        BaseMod.addCard(new crystallizedlightning());
        BaseMod.addCard(new crystallizedfrost());
        BaseMod.addCard(new crystallizeddark());
    }

    @Override
    public void receiveEditRelics()
    {
        BaseMod.addRelic(new Matchstick(), RelicType.SHARED);
        BaseMod.addRelic(new HotPepper(), RelicType.SHARED);
    }

    @Override
    public void receiveEditStrings() {
        loadCustomStringsFile(CardStrings.class,"resources/localization/cardStrings.json");
        loadCustomStringsFile(RelicStrings.class, "resources/localization/relicStrings.json");
    }


}

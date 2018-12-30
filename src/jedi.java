package sts_jedi;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.*;
import gluttonmod.patches.AbstractCardEnum;
import mod.jedi.cards.blue.*;
import mod.jedi.cards.colorless.Cleanse;
import mod.jedi.cards.colorless.Forcepull;
import mod.jedi.cards.colorless.Forcepush;
import mod.jedi.cards.curses.Frostbite;
import mod.jedi.cards.red.*;
import mod.jedi.events.SwordDojo;
import mod.jedi.potions.CoolantLeak;
import mod.jedi.potions.HolyWater;
import mod.jedi.potions.TentacleJuice;
import mod.jedi.relics.*;

import static basemod.BaseMod.loadCustomStringsFile;

@SpireInitializer
public class jedi
        implements
            PostInitializeSubscriber,
            EditRelicsSubscriber,
            EditStringsSubscriber,
            EditCardsSubscriber,
            MaxHPChangeSubscriber
{

    public static boolean isReplayLoaded;
    public static boolean isConspireLoaded;
    public static boolean isGluttonLoaded;
    public static boolean isBeakedLoaded;

    public static void initialize()
    {
        BaseMod.subscribe(new jedi());
        isReplayLoaded = Loader.isModLoaded("ReplayTheSpireMod");
        isConspireLoaded = Loader.isModLoaded("conspire");
        isGluttonLoaded = Loader.isModLoaded("GluttonMod");
        isBeakedLoaded = Loader.isModLoaded("beakedthecultist-sts");

    }

//    		BaseMod.addPotion(potionClass, liquidColor, hybridColor, spotsColor, potionID);

    @Override
    public void receivePostInitialize()
    {
        // Potions
        BaseMod.addPotion(TentacleJuice.class, Color.PURPLE,null,Color.WHITE,TentacleJuice.ID);
        BaseMod.addPotion(CoolantLeak.class, null, Color.CYAN, null,CoolantLeak.ID, AbstractPlayer.PlayerClass.DEFECT);
        BaseMod.addPotion(HolyWater.class, Color.YELLOW, Color.WHITE, null, HolyWater.ID);

        // Events
        BaseMod.addEvent(SwordDojo.ID, SwordDojo.class, TheCity.ID);
    }

    @Override
    public void receiveEditCards()
    {
        //Colorless
        BaseMod.addCard(new Cleanse());
        BaseMod.addCard(new Forcepush());
        BaseMod.addCard(new Forcepull());

        //Blue
        BaseMod.addCard(new BurstLightning());
        BaseMod.addCard(new BurstFrost());
        BaseMod.addCard(new BurstDark());
        BaseMod.addCard(new MarkOfDeath());
        BaseMod.addCard(new LockNLoad());
        BaseMod.addCard(new Hex());
        BaseMod.addCard(new Sharpshooter());
        BaseMod.addCard(new ReadyAimFire());
        BaseMod.addCard(new Reiji());
        BaseMod.addCard(new BlockOn());
        BaseMod.addCard(new GatheringStorm());
        BaseMod.addCard(new Meditation());
        BaseMod.addCard(new DarknessCall());

        //Red
        BaseMod.addCard(new StrikingStrike());
        BaseMod.addCard(new OneStrike());
        //Fear leads to anger, anger leads to hate, hate leads to suffering
        BaseMod.addCard(new Fear());
        BaseMod.addCard(new Hate());
        BaseMod.addCard(new Suffering());

        //Curses
        BaseMod.addCard(new Frostbite());
    }

    @Override
    public void receiveEditRelics()
    {
        BaseMod.addRelic(new HotPepper(), RelicType.SHARED);
        BaseMod.addRelic(new Endoplasm(), RelicType.SHARED);
        BaseMod.addRelic(new Matchstick(), RelicType.SHARED);
        BaseMod.addRelic(new LeadLinedBottle(), RelicType.SHARED);
        BaseMod.addRelic(new FakeMustache(), RelicType.SHARED);
        BaseMod.addRelic(new FirstAidKit(), RelicType.SHARED);
        BaseMod.addRelic(new CrownOfSimplicity(), RelicType.SHARED);
        BaseMod.addRelic(new HeavyJacket(), RelicType.SHARED);
        BaseMod.addRelic(new ShrinkRay(), RelicType.SHARED);

        BaseMod.addRelic(new LaserPointer(), RelicType.BLUE);
        BaseMod.addRelic(new Superconductor(), RelicType.BLUE);
        BaseMod.addRelic(new PaperFaux(), RelicType.BLUE);

        BaseMod.addRelic(new ScrapMetal(), RelicType.GREEN);

        BaseMod.addRelic(new Leech(), RelicType.RED);

        BaseMod.addRelic(new Zontanonomicon(), RelicType.SHARED);

        if (isGluttonLoaded)
        {
            Leech glutLeech = new Leech();
            BaseMod.addRelicToCustomPool(glutLeech, AbstractCardEnum.GLUTTON);
            RelicLibrary.uncommonList.remove(glutLeech);
        }

        if (isBeakedLoaded)
        {
            Leech beakedLeech = new Leech();
            BaseMod.addRelicToCustomPool(beakedLeech, beaked.patches.AbstractCardEnum.BEAKED_YELLOW);
            RelicLibrary.uncommonList.remove(beakedLeech);
        }


//        if (isReplayLoaded) {
//            BaseMod.addRelic(new OtherSneckoEye(), RelicType.SHARED);
//        }
    }

    @Override
    public void receiveEditStrings() {
        loadCustomStringsFile(CardStrings.class,"resources/jedi/localization/cardStrings.json");
        loadCustomStringsFile(RelicStrings.class, "resources/jedi/localization/relicStrings.json");
        loadCustomStringsFile(PotionStrings.class, "resources/jedi/localization/potionStrings.json");
        loadCustomStringsFile(PowerStrings.class, "resources/jedi/localization/powerStrings.json");
        loadCustomStringsFile(EventStrings.class, "resources/jedi/localization/eventStrings.json");
    }

    @Override
    public int receiveMapHPChange(int amount) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(HotPepper.ID)) {
            HotPepper relic = (HotPepper) AbstractDungeon.player.getRelic(HotPepper.ID);
            amount = relic.onMaxHPChange(amount);
        }
        return amount;
    }

    public static AbstractCard returnTrulyRandomStrike()
    {
        final CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : CardLibrary.getAllCards())
        {
            if ((card.hasTag(AbstractCard.CardTags.STRIKE) && (card.rarity != AbstractCard.CardRarity.BASIC)))
            {
                group.addToBottom(card.makeCopy());
            }
        }
        return group.getRandomCard(true).makeCopy();
    }
}

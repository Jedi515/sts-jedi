package sts_jedi;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.*;
import conspire.cards.colorless.GhostlyStrike;
import gluttonmod.patches.AbstractCardEnum;
import mod.jedi.cards.blue.*;
import mod.jedi.cards.colorless.Cleanse;
import mod.jedi.cards.colorless.Forcepull;
import mod.jedi.cards.colorless.Forcepush;
import mod.jedi.cards.curses.Frostbite;
import mod.jedi.cards.green.PoisonIvy;
import mod.jedi.cards.red.*;
import mod.jedi.events.SwordDojo;
import mod.jedi.potions.*;
import mod.jedi.relics.*;

import java.nio.charset.StandardCharsets;

import static basemod.BaseMod.loadCustomStrings;
import static the_gatherer.GathererMod.lesserPotionPool;

@SpireInitializer
public class jedi
        implements
            PostInitializeSubscriber,
            EditRelicsSubscriber,
            EditStringsSubscriber,
            EditCardsSubscriber,
            EditKeywordsSubscriber,
            MaxHPChangeSubscriber
{
    public static boolean isReplayLoaded;
    public static boolean isConspireLoaded;
    public static boolean isGluttonLoaded;
    public static boolean isBeakedLoaded;
    public static boolean isGathererLoaded;
    public static boolean isHubrisLoaded;

    public static void initialize()
    {
        BaseMod.subscribe(new jedi());
        isReplayLoaded = Loader.isModLoaded("ReplayTheSpireMod");
        isConspireLoaded = Loader.isModLoaded("conspire");
        isGluttonLoaded = Loader.isModLoaded("GluttonMod");
        isBeakedLoaded = Loader.isModLoaded("beakedthecultist-sts");
        isGathererLoaded = Loader.isModLoaded("gatherermod");
        isHubrisLoaded = Loader.isModLoaded("hubris");
    }

//    		BaseMod.addPotion(potionClass, liquidColor, hybridColor, spotsColor, potionID);

    @Override
    public void receivePostInitialize()
    {
        // Potions
        BaseMod.addPotion(TentacleJuice.class, Color.PURPLE,null,Color.WHITE,TentacleJuice.ID);
        BaseMod.addPotion(CoolantLeak.class, null, Color.CYAN, null,CoolantLeak.ID, AbstractPlayer.PlayerClass.DEFECT);
        BaseMod.addPotion(HolyWater.class, Color.YELLOW, Color.WHITE, null, HolyWater.ID);

        if (isGathererLoaded)
        {
            lesserPotionPool.add(new LesserTentacleJuice());
            lesserPotionPool.add(new LesserHolyWater());
        }

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

        //Green
        BaseMod.addCard(new PoisonIvy());

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
        BaseMod.addCard(new Bruteforce());

        //Red
        BaseMod.addCard(new StrikingStrike());
        BaseMod.addCard(new OneStrike());
        BaseMod.addCard(new CollectorStrike());
        //Fear leads to anger, anger leads to hate, hate leads to suffering
        BaseMod.addCard(new Fear());
        BaseMod.addCard(new Hate());
        BaseMod.addCard(new Suffering());
        //WORK IT / MAKE IT / DO IT / MAKES US
        BaseMod.addCard(new Harder());
        BaseMod.addCard(new Better());
        BaseMod.addCard(new Faster());
        BaseMod.addCard(new Stronger());
        //UNLIMITED PAAAWAAAAAAAH
        BaseMod.addCard(new UnlimitedPower());
        BaseMod.addCard(new BloodyHammer());
        BaseMod.addCard(new ControlledAnger());

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
        BaseMod.addRelic(new GremlinKnob(), RelicType.SHARED);
        //This one is special cuz it's usually ironchad-only, except if player somewhy picks up black hole from hubris or is glutton.
        BaseMod.addRelic(new AshLotus(), RelicType.SHARED);

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


        if (isReplayLoaded) {
            BaseMod.addRelic(new OtherSneckoEye(), RelicType.SHARED);
        }
    }

//  Copied from Gatherer
    private static String GetLocString(String locCode, String name) {
        return Gdx.files.internal("resources/jedi/localization/" + locCode + "/" + name + ".json").readString(
                String.valueOf(StandardCharsets.UTF_8));
    }

    private static String getLocCode() {
        switch (Settings.language) {
            case RUS:
                return "rus";
            case ENG:
                return "eng";


            default:
                return "eng";
        }
    }

    @Override
    public void receiveEditKeywords()
    {

        Gson gson = new Gson();
        String loc = getLocCode();

        String json = GetLocString(loc, "keywordStrings");
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditStrings() {

        String loc = getLocCode();

        String cardStrings = GetLocString(loc, "cardStrings");
        loadCustomStrings(CardStrings.class, cardStrings);

        String relicStrings = GetLocString(loc, "relicStrings");
        loadCustomStrings(RelicStrings.class, relicStrings);

        String potionStrings = GetLocString(loc, "potionStrings");
        loadCustomStrings(PotionStrings.class, potionStrings);

        String powerStrings = GetLocString(loc, "powerStrings");
        loadCustomStrings(PowerStrings.class, powerStrings);

        String eventStrings = GetLocString(loc, "eventStrings");
        loadCustomStrings(EventStrings.class, eventStrings);

        String uiStrings = GetLocString(loc, "uiStrings");
        loadCustomStrings(UIStrings.class, uiStrings);
    }

    @Override
    public int receiveMaxHPChange(int amount) {
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
                if (isConspireLoaded)
                {
                    if (card instanceof GhostlyStrike)
                    {
                        continue;
                    }
                }
                group.addToBottom(card.makeCopy());
            }
        }
        return group.getRandomCard(true).makeCopy();
    }

    //As unused as it is now, will be useful for when kio makes customDiscovery action or something.
    public CardGroup descriptionSearch(String[] keywords)
    {
        CardGroup toReturn = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        for (AbstractCard c : CardLibrary.getAllCards())
        {
            for (String keyword : keywords)
            {
                if ((   c.rawDescription.toLowerCase().contains(keyword.toLowerCase()) &&
                        !(toReturn.contains(c)) &&
                        (c.type != AbstractCard.CardType.CURSE) &&
                        (c.type != AbstractCard.CardType.STATUS)))
                {
                    toReturn.addToBottom(c.makeCopy());
                    //don't forget to markAsSeen when it pops on player screen
                    break;
                }
            }
        }
        return toReturn;
    }
}

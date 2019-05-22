package mod.jedi;

import archetypeAPI.ArchetypeAPI;
import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.helpers.BaseModCardTags;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CallingBell;
import com.megacrit.cardcrawl.relics.PandorasBox;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import gluttonmod.patches.AbstractCardEnum;
import mod.jedi.cards.blue.*;
import mod.jedi.cards.colorless.Cleanse;
import mod.jedi.cards.colorless.Forcepull;
import mod.jedi.cards.colorless.Forcepush;
import mod.jedi.cards.curses.Frostbite;
import mod.jedi.cards.curses.TheDog;
import mod.jedi.cards.green.*;
import mod.jedi.cards.red.*;
import mod.jedi.events.SwordDojo;
import mod.jedi.interfaces.RelicOnFullAttackMonster;
import mod.jedi.modifiers.CommandCustomRun;
import mod.jedi.relics.Equalizer;
import mod.jedi.potions.*;
import mod.jedi.relics.*;
import mod.jedi.util.TextureLoader;
import mod.jedi.variables.JediSecondMN;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
            MaxHPChangeSubscriber,
            PostUpdateSubscriber,
            AddCustomModeModsSubscriber,
            PostDungeonInitializeSubscriber,
            RelicGetSubscriber
{
    public static boolean isReplayLoaded;
    public static boolean isConspireLoaded;
    public static boolean isGluttonLoaded;
    public static boolean isBeakedLoaded;
    public static boolean isGathererLoaded;
    public static boolean isHubrisLoaded;
    public static boolean isArchetypeLoaded;
    public static CardGroup StrikeGroup;
    public static SpireConfig jediConfig;
    public static boolean CommandUnseen;
    public static boolean CommandLocked;
    public static boolean CommandHasCopy;
    public static ArrayList<String> cursedRelics = new ArrayList<>();
    public static ArrayList<AbstractRelic> unseenRelics = new ArrayList<>();
    public static ArrayList<String> lockedRelics = new ArrayList<>();

    public static void initialize()
    {
        BaseMod.subscribe(new jedi());
        isReplayLoaded = Loader.isModLoaded("ReplayTheSpireMod");
        isConspireLoaded = Loader.isModLoaded("conspire");
        isGluttonLoaded = Loader.isModLoaded("GluttonMod");
        isBeakedLoaded = Loader.isModLoaded("beakedthecultist-sts");
        isGathererLoaded = Loader.isModLoaded("gatherermod");
        isHubrisLoaded = Loader.isModLoaded("hubris");
        isArchetypeLoaded = Loader.isModLoaded("archetypeapi");
    }

    public static int publishAttackMonsterChange(DamageInfo info, int damage)
    {
        int returnDamage = damage;
        for (AbstractRelic r : AbstractDungeon.player.relics)
        {
            if (r instanceof RelicOnFullAttackMonster)
            {
                returnDamage = ((RelicOnFullAttackMonster)r).betterOnAttackedMonster(info, returnDamage);
            }
        }
        return returnDamage;
    }

//    		BaseMod.addPotion(potionClass, liquidColor, hybridColor, spotsColor, potionID);

    @Override
    public void receivePostInitialize()
    {
        // Potions
        BaseMod.addPotion(TentacleJuice.class, Color.PURPLE,null,Color.WHITE,TentacleJuice.ID);
        BaseMod.addPotion(CoolantLeak.class, null, Color.CYAN, null,CoolantLeak.ID, AbstractPlayer.PlayerClass.DEFECT);
        BaseMod.addPotion(HolyWater.class, Color.YELLOW, Color.WHITE, null, HolyWater.ID);

        // Filling cursed relic pool for lucky charm
        cursedRelics.clear();
        cursedRelics.add(CallingBell.ID);
        cursedRelics.add(PandorasBox.ID);
        cursedRelics.add(OminousLoanNote.ID);
        cursedRelics.add(ScalesOfToshan.ID);
        cursedRelics.add(Equalizer.ID);
        cursedRelics.add(LuckyCharm.ID);

        if (isGathererLoaded)
        {
            lesserPotionPool.add(new LesserTentacleJuice());
            lesserPotionPool.add(new LesserHolyWater());
        }

        // Events
        BaseMod.addEvent(SwordDojo.ID, SwordDojo.class, TheCity.ID);

        StrikeGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard card : CardLibrary.getAllCards())
        {
            if ((card.hasTag(AbstractCard.CardTags.STRIKE) &&
                (!card.hasTag(BaseModCardTags.BASIC_STRIKE)) &&
                (card.rarity != AbstractCard.CardRarity.BASIC)))
            {
                StrikeGroup.addToBottom(card.makeCopy());
            }
        }

        if (isArchetypeLoaded)
        {
            ArchetypeAPI.loadArchetypes("resources/jedi/Archetypes");
        }

        //Buttons
        try {
            Properties defaults = new Properties();
            defaults.put("jedi:commandunseen", Boolean.toString(false));
            defaults.put("jedi:commandlocked", Boolean.toString(false));
            defaults.put("jedi:commandhascopy", Boolean.toString(false));

            jediConfig = new SpireConfig("jedi","jediConfig", defaults);
            jediConfig.load();
            CommandUnseen = jediConfig.getBool("jedi:commandunseen");
            CommandLocked = jediConfig.getBool("jedi:commandlocked");
            CommandHasCopy = jediConfig.getBool("jedi:commandhascopy");

        } catch (IOException e) {
            e.printStackTrace();
        }
        ModPanel settingsPanel = new ModPanel();

        loadConfigButtons(settingsPanel);

        RelicLibrary.specialList.removeIf(r -> r.relicId.contains("jedi:command_"));
    }

    private void loadConfigButtons(ModPanel settingsPanel)
    {
        UIStrings commandUI = CardCrawlGame.languagePack.getUIString("jedi:Command");
        String[] TEXT = commandUI.TEXT;
        ModLabeledToggleButton cmdUnseenBtn = new ModLabeledToggleButton(TEXT[0],
                350, 600, Settings.CREAM_COLOR, FontHelper.charDescFont,
                CommandUnseen, settingsPanel, l -> {},
                button ->
                {
                    if (jediConfig != null) {
                        jediConfig.setBool("jedi:commandunseen", button.enabled);
                        try {
                            jediConfig.save();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        settingsPanel.addUIElement(cmdUnseenBtn);
        ModLabeledToggleButton cmdLockedBtn = new ModLabeledToggleButton(TEXT[1],
                350, 550, Settings.CREAM_COLOR, FontHelper.charDescFont,
                CommandLocked, settingsPanel, l -> {},
                button ->
                {
                    if (jediConfig != null) {
                        jediConfig.setBool("jedi:commandlocked", button.enabled);
                        try {
                            jediConfig.save();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        settingsPanel.addUIElement(cmdLockedBtn);

        ModLabeledToggleButton cmdHascopyBtn = new ModLabeledToggleButton(TEXT[2],
                350, 500, Settings.CREAM_COLOR, FontHelper.charDescFont,
                CommandHasCopy, settingsPanel, l -> {},
                button ->
                {
                    if (jediConfig != null) {
                        jediConfig.setBool("jedi:commandhascopy", button.enabled);
                        try {
                            jediConfig.save();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        settingsPanel.addUIElement(cmdHascopyBtn);

        ModLabel lblCmdWarning = new ModLabel(TEXT[3], 350, 450, Settings.CREAM_COLOR, FontHelper.charDescFont,settingsPanel, l -> {});
        settingsPanel.addUIElement(lblCmdWarning);

        BaseMod.registerModBadge(TextureLoader.getTexture("resources/jedi/images/badge.png"), "Jedi", "Jedi#3970", "Wat", settingsPanel);
    }

    @Override
    public void receiveEditCards()
    {
        BaseMod.addDynamicVariable(new JediSecondMN());
        //Colorless
        BaseMod.addCard(new Cleanse());
        BaseMod.addCard(new Forcepush());
        BaseMod.addCard(new Forcepull());

        //Green
        BaseMod.addCard(new PoisonIvy());
        BaseMod.addCard(new CollectorVenom());
        BaseMod.addCard(new UnstableFumes());
        BaseMod.addCard(new PocketPoison());
        BaseMod.addCard(new BrewingPoison());
        BaseMod.addCard(new RejectiveToxin());
        BaseMod.addCard(new AcidRain());

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
        BaseMod.addCard(new SpotBugs());
        BaseMod.addCard(new Overflow());

        //Red
        BaseMod.addCard(new StrikingStrike());
        BaseMod.addCard(new OneStrike());
        BaseMod.addCard(new CollectorStrike());
        BaseMod.addCard(new Fear());
        BaseMod.addCard(new Hate());
        BaseMod.addCard(new Suffering());
        BaseMod.addCard(new Harder());
        BaseMod.addCard(new Better());
        BaseMod.addCard(new Faster());
        BaseMod.addCard(new Stronger());
        BaseMod.addCard(new UnlimitedPower());
        BaseMod.addCard(new BloodyHammer());
        BaseMod.addCard(new ControlledAnger());

        //Curses
        BaseMod.addCard(new Frostbite());
        BaseMod.addCard(new TheDog());
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
        BaseMod.addRelic(new PurpleFairy(), RelicType.SHARED);
        BaseMod.addRelic(new CryingMask(), RelicType.SHARED);
        BaseMod.addRelic(new BattleStandard(), RelicType.SHARED);
        BaseMod.addRelic(new Kaleidoscope(), RelicType.SHARED);
        BaseMod.addRelic(new PaperLyon(), RelicType.SHARED);
        BaseMod.addRelic(new AngryMask(), RelicType.SHARED);
        BaseMod.addRelic(new ArchwizardHat(), RelicType.SHARED);
        BaseMod.addRelic(new WindUpBox(), RelicType.SHARED);
        BaseMod.addRelic(new BottledFury(), RelicType.SHARED);
        BaseMod.addRelic(new ArcaneWood(), RelicType.SHARED);
        BaseMod.addRelic(new StrikeManual(), RelicType.SHARED);
        BaseMod.addRelic(new OminousLoanNote(), RelicType.SHARED);
        BaseMod.addRelic(new ScalesOfToshan(), RelicType.SHARED);
        BaseMod.addRelic(new Equalizer(), RelicType.SHARED);
        BaseMod.addRelic(new LuckyCharm(), RelicType.SHARED);

        BaseMod.addRelic(new TokenOfWealth(), RelicType.SHARED);
        BaseMod.addRelic(new TokenOfGlory(), RelicType.SHARED);
        BaseMod.addRelic(new TokenOfMystery(), RelicType.SHARED);
        BaseMod.addRelic(new TokenOfSerenity(), RelicType.SHARED);

        BaseMod.addRelic(new StrengthTestRelic(), RelicType.SHARED);

        if (isHubrisLoaded)
        {
            BaseMod.addRelic(new MainCommand(), RelicType.SHARED);
            BaseMod.addRelic(new Command_common(), RelicType.SHARED);
            BaseMod.addRelic(new Command_uncommon(), RelicType.SHARED);
            BaseMod.addRelic(new Command_rare(), RelicType.SHARED);
            BaseMod.addRelic(new Command_shop(), RelicType.SHARED);
            BaseMod.addRelic(new Command_boss(), RelicType.SHARED);
        }
        //This one is special cuz it's usually ironchad-only, except if player somewhy picks up black hole from hubris or is glutton.
        BaseMod.addRelic(new AshLotus(), RelicType.SHARED);

        BaseMod.addRelic(new LaserPointer(), RelicType.BLUE);
        BaseMod.addRelic(new Superconductor(), RelicType.BLUE);
        BaseMod.addRelic(new PaperFaux(), RelicType.BLUE);

        BaseMod.addRelic(new ScrapMetal(), RelicType.GREEN);
        BaseMod.addRelic(new Sprinkler(), RelicType.GREEN);
        BaseMod.addRelic(new GhostBlades(), RelicType.GREEN);

        BaseMod.addRelic(new Leech(), RelicType.RED);
        BaseMod.addRelic(new IronBlood(), RelicType.RED);

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


        if (isReplayLoaded)
        {
            BaseMod.addRelic(new OtherSneckoEye(), RelicType.SHARED);
        }
    }

//  Copied from Gatherer
    private static String GetLocString(String locCode, String name) {
        return Gdx.files.internal("resources/jedi/localization/" + locCode + "/" + name + ".json").readString(
                String.valueOf(StandardCharsets.UTF_8));
    }

    @Override
    public void receiveEditKeywords()
    {
        loadKeywords("eng");
        if (Settings.language != Settings.GameLanguage.ENG)
        {
            try
            {
                loadKeywords(Settings.language.toString().toLowerCase());
            }
            catch (GdxRuntimeException er)
            {
                System.out.println("Jedi Mod: Adding keywords error: Language not found, defaulted to eng.");
            }
        }
    }

    private void loadKeywords(String langKey)
    {
        Gson gson = new Gson();

        String json = GetLocString(langKey, "keywordStrings");
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords)
            {
                BaseMod.addKeyword("jedi", keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditStrings()
    {
        loadStrings("eng");
        if (Settings.language != Settings.GameLanguage.ENG)
        {
            try
            {
                loadStrings(Settings.language.toString().toLowerCase());
            }
            catch (GdxRuntimeException er)
            {
                System.out.println("Jedi Mod: Adding strings error: Language not found, defaulted to eng.");
            }
        }
    }

    private void loadStrings(String langKey)
    {
        loadCustomStrings(CardStrings.class, GetLocString(langKey, "cardStrings"));
        loadCustomStrings(RelicStrings.class, GetLocString(langKey, "relicStrings"));
        loadCustomStrings(PotionStrings.class, GetLocString(langKey, "potionStrings"));
        loadCustomStrings(PowerStrings.class, GetLocString(langKey, "powerStrings"));
        loadCustomStrings(EventStrings.class, GetLocString(langKey, "eventStrings"));
        loadCustomStrings(UIStrings.class, GetLocString(langKey, "uiStrings"));
        loadCustomStrings(RunModStrings.class, GetLocString(langKey, "runmodStrings"));
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
        return StrikeGroup.getRandomCard(true).makeCopy();
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

    @Override
    public void receivePostUpdate()
    {
        if (AbstractDungeon.player == null) return;

        if (isHubrisLoaded)
        {
            AbstractCommand command = null;

            for (AbstractRelic r : AbstractDungeon.player.relics)
            {
                if (r.relicId.contains(AbstractCommand.ID))
                {
                    command = (AbstractCommand) r;
                }
            }

            if (command != null)
            {
                command.relicBS();
            }
        }
    }

    @Override
    public void receiveCustomModeMods(List<CustomMod> list)
    {
        if (isHubrisLoaded)
        {
            list.add(new CustomMod(CommandCustomRun.ID, "b", true));
        }
    }

    @Override
    public void receivePostDungeonInitialize()
    {
        if (isHubrisLoaded)
        {
            if (CardCrawlGame.trial != null)
            {
                if( CardCrawlGame.trial.dailyModIDs().contains(CommandCustomRun.ID))
                {
                    new MainCommand().instantObtain();
                }
            }
        }
        CommandUnseen = jediConfig.getBool("jedi:commandunseen");
        CommandLocked = jediConfig.getBool("jedi:commandlocked");
        CommandHasCopy = jediConfig.getBool("jedi:commandhascopy");
    }

    @Override
    public void receiveRelicGet(AbstractRelic abstractRelic)
    {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) return;
        for (AbstractRelic r : p.relics)
        {
            if (r.relicId.equals(HeavyJacket.ID)) ((HeavyJacket)r).modifyCounter();
            if (r.relicId.equals(LuckyCharm.ID)) ((LuckyCharm)r).modifyCounter(abstractRelic.relicId);
        }

    }
}

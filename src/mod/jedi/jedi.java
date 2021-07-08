package mod.jedi;

import archetypeAPI.ArchetypeAPI;
import basemod.*;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
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
import com.megacrit.cardcrawl.cards.blue.Scrape;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AccuracyPower;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import gluttonmod.patches.AbstractCardEnum;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;
import mod.jedi.cardMods.gathererEssenceMods.AbstractEssenceMod;
import mod.jedi.cards.CustomJediCard;
import mod.jedi.cards.blue.ScrapePlus;
import mod.jedi.events.GuildOfFate;
import mod.jedi.events.ShrineOfCommand;
import mod.jedi.events.SwordDojo;
import mod.jedi.interfaces.onGenerateCardMidcombatInterface;
import mod.jedi.modifiers.CommandCustomRun;
import mod.jedi.modifiers.WarmongerRunMod;
import mod.jedi.potions.*;
import mod.jedi.potions.HolyWater;
import mod.jedi.relics.*;
import mod.jedi.util.TextureLoader;
import mod.jedi.variables.JediSecondMN;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
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
    public static CardGroup poisonGroup;
    public static CardGroup shivGroup;
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

//    		BaseMod.addPotion(potionClass, liquidColor, hybridColor, spotsColor, potionID);

    @Override
    public void receivePostInitialize()
    {
        // Potions
        BaseMod.addPotion(TentacleJuice.class, Color.PURPLE,null, Color.WHITE, TentacleJuice.ID);
        BaseMod.addPotion(CoolantLeak.class, null, Color.CYAN, null, CoolantLeak.ID, AbstractPlayer.PlayerClass.DEFECT);
        BaseMod.addPotion(HolyWater.class, Color.YELLOW, Color.WHITE, null, HolyWater.ID);
        BaseMod.addPotion(HoardPotion.class, Color.GOLD, Color.CLEAR, null, HoardPotion.ID);
        BaseMod.addPotion(MysteryPotion.class, Color.GOLD, Color.CLEAR, null, MysteryPotion.ID);
        BaseMod.addPotion(WingPotion.class, Color.GOLD, Color.CLEAR, null, WingPotion.ID);
        BaseMod.addPotion(StrikingPotion.class, Color.RED, Color.CLEAR, Color.WHITE, StrikingPotion.ID);

        // Filling cursed relic pool for lucky charm
        cursedRelics.clear();
        cursedRelics.add(CallingBell.ID);
        cursedRelics.add(PandorasBox.ID);
        cursedRelics.add(OminousLoanNote.ID);
        cursedRelics.add(ScalesOfToshan.ID);
        cursedRelics.add(Equalizer.ID);
        cursedRelics.add(LuckyCharm.ID);
        Collections.shuffle(cursedRelics);

        if (isGathererLoaded)
        {
            lesserPotionPool.add(new LesserTentacleJuice());
            lesserPotionPool.add(new LesserHolyWater());
        }

        // Events
        BaseMod.addEvent(SwordDojo.ID, SwordDojo.class, TheCity.ID);
        BaseMod.addEvent(new AddEventParams
                .Builder(ShrineOfCommand.ID, ShrineOfCommand.class)
                .spawnCondition(() -> AbstractDungeon.actNum > 1)
                .eventType(EventUtils.EventType.SHRINE)
                .create()
        );
        BaseMod.addEvent(new AddEventParams
                .Builder(GuildOfFate.ID, GuildOfFate.class)
                .spawnCondition(() -> AbstractDungeon.actNum == 1 || Settings.isEndless)
                .eventType(EventUtils.EventType.ONE_TIME)
                .create());

        StrikeGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        poisonGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        shivGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        String[] poisons = {"poison", "venom"};
        String[] shivs = {"shiv", AccuracyPower.class.getSimpleName().toLowerCase()};

        for (AbstractCard card : CardLibrary.getAllCards())
        {

            if ((card.hasTag(AbstractCard.CardTags.STRIKE) &&
                (!card.hasTag(AbstractCard.CardTags.STARTER_STRIKE)) &&
                (card.rarity != AbstractCard.CardRarity.BASIC)))
            {
                StrikeGroup.addToBottom(card.makeCopy());
            }
            boolean extraCheck = false;
            for (String name : GameDictionary.POISON.NAMES)
            {
                if (card.keywords.contains(name))
                {
                    extraCheck = true;
                    break;
                }
            }

            if (   !(card.hasTag(AbstractCard.CardTags.HEALING)) &&
                    (card.rarity != AbstractCard.CardRarity.SPECIAL) &&
                    (card.rarity != AbstractCard.CardRarity.BASIC) &&
                    (extraCheck || checkIfContainsWords(card, poisons)))
            {
                poisonGroup.addToBottom(card.makeCopy());
            }

            extraCheck = false;
            for (String name : GameDictionary.SHIV.NAMES)
            {
                if (card.keywords.contains(name))
                {
                    extraCheck = true;
                    break;
                }
            }

            if (   !(card.hasTag(AbstractCard.CardTags.HEALING)) &&
                    (card.rarity != AbstractCard.CardRarity.SPECIAL) &&
                    (card.rarity != AbstractCard.CardRarity.BASIC) &&
                    !(card.cardID.equals(Shiv.ID)) &&
                    (extraCheck || checkIfContainsWords(card, shivs))
            )
            shivGroup.addToBottom(card.makeCopy());
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

    private boolean checkIfContainsWords(AbstractCard card, String[] words)
    {
        for (String s : words)
        {
            if (checkIfContainsWord(card, s)) return true;
        }
        return false;
    }

    private boolean checkIfContainsWord(AbstractCard card, String word)
    {
        boolean toReturn = false;
        if (card.cardID.toLowerCase().contains(word)) return true;
        for (String kw : card.keywords)
        {
            if (kw.toLowerCase().equals(word)) return true;
        }
        if (card.getClass().getName().toLowerCase().contains(word)) return true;
        try {
            CtClass ctClass = Loader.getClassPool().get(card.getClass().getName());
            ctClass.defrost();

            String[] methods = {"use", "triggerOnManualDiscard", "triggerWhenDrawn", "calculateCardDamage", "triggerOnExhaust"};

            toReturn = ifFunctionsContainWord(ctClass, methods, word);
            ctClass.freeze();
        } catch (NotFoundException ignored)
        {

        }
        return toReturn;
    }

    private boolean ifFunctionsContainWord(CtClass ctClass, String[] methods, String word)
    {
        for (String s : methods)
        {
            if (ifFunctionContainsWord(ctClass, s, word)) return true;
        }

        return false;
    }

    private boolean ifFunctionContainsWord(CtClass ctClass, String method, String word)
    {
        final boolean[] toReturn = {false};
        try
        {

            CtMethod ctMethod = ctClass.getDeclaredMethod(method);

            ExprEditor wordNewExprFinder = new ExprEditor(){
                @Override
                public void edit(NewExpr e)
                {
                    if (e.getClassName().toLowerCase().contains(word))
                    {
                        toReturn[0] = true;
                    }
                }
            };

            ExprEditor wordMethodCallFinder = new ExprEditor(){
                @Override
                public void edit(MethodCall m)
                {
                    if (m.getClassName().toLowerCase().contains(word))
                    {
                        toReturn[0] = true;
                    }
                }
            };

            ExprEditor wordFieldAccessFinder = new ExprEditor(){
                @Override
                public void edit(FieldAccess f)
                {
                    if (f.getClassName().toLowerCase().contains(word))
                    {
                        toReturn[0] = true;
                    }
                }
            };

            ctMethod.instrument(wordNewExprFinder);
            if (!toReturn[0]) ctMethod.instrument(wordFieldAccessFinder);
            if (!toReturn[0]) ctMethod.instrument(wordMethodCallFinder);

        } catch (NotFoundException | CannotCompileException e) {
        }
        return toReturn[0];
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
//        BaseMod.removeCard(Scrape.ID, AbstractCard.CardColor.BLUE);
//        BaseMod.addCard(new ScrapePlus());
        new AutoAdd("jedi").packageFilter(CustomJediCard.class).setDefaultSeen(true).cards();
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
//        BaseMod.addRelic(new ScalesOfToshan(), RelicType.SHARED);
//        BaseMod.addRelic(new Equalizer(), RelicType.SHARED);
//        BaseMod.addRelic(new LuckyCharm(), RelicType.SHARED);
        BaseMod.addRelic(new Pinwheel(), RelicType.SHARED);
        BaseMod.addRelic(new HeartOfTheCards(), RelicType.SHARED);
        BaseMod.addRelic(new ByrdBible(), RelicType.SHARED);
        BaseMod.addRelic(new PaintBrush(), RelicType.SHARED);
        BaseMod.addRelic(new StarAurum(), RelicType.SHARED);
        BaseMod.addRelic(new FennexFeather(), RelicType.SHARED);


        BaseMod.addRelic(new TokenOfWealth(), RelicType.SHARED);
        BaseMod.addRelic(new TokenOfGlory(), RelicType.SHARED);
        BaseMod.addRelic(new TokenOfMystery(), RelicType.SHARED);
        BaseMod.addRelic(new TokenOfSerenity(), RelicType.SHARED);

        BaseMod.addRelic(new MainCommand(), RelicType.SHARED);
        BaseMod.addRelic(new Command_common(), RelicType.SHARED);
        BaseMod.addRelic(new Command_uncommon(), RelicType.SHARED);
        BaseMod.addRelic(new Command_rare(), RelicType.SHARED);
        BaseMod.addRelic(new Command_shop(), RelicType.SHARED);
        BaseMod.addRelic(new Command_boss(), RelicType.SHARED);

        //This one is special cuz it's usually ironchad-only, except if player somewhy picks up black hole from hubris or is glutton.
        BaseMod.addRelic(new AshLotus(), RelicType.SHARED);

        BaseMod.addRelic(new LaserPointer(), RelicType.BLUE);
        BaseMod.addRelic(new Superconductor(), RelicType.BLUE);
        BaseMod.addRelic(new PaperFaux(), RelicType.BLUE);

        BaseMod.addRelic(new ScrapMetal(), RelicType.GREEN);
        BaseMod.addRelic(new Sprinkler(), RelicType.GREEN);
        BaseMod.addRelic(new GhostBlades(), RelicType.GREEN);
        BaseMod.addRelic(new RubberSling(), RelicType.GREEN);
        BaseMod.addRelic(new Catwich(), RelicType.GREEN);

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
        if (!Gdx.files.internal("resources/jedi/localization/" + langKey + "/").exists()) return;
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
        if (!Gdx.files.internal("resources/jedi/localization/" + langKey + "/").exists()) return;
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

    @Override
    public void receivePostUpdate()
    {
        if (AbstractDungeon.player == null) return;

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

    @Override
    public void receiveCustomModeMods(List<CustomMod> list)
    {
        list.add(new CustomMod(CommandCustomRun.ID, "b", true));
        list.add(new CustomMod(WarmongerRunMod.ID, "r", true));
    }

    @Override
    public void receivePostDungeonInitialize()
    {
        if (CardCrawlGame.trial != null)
        {
            if (CardCrawlGame.trial.dailyModIDs().contains(CommandCustomRun.ID))
            {
                new MainCommand().instantObtain();
            }
            if (CardCrawlGame.trial.dailyModIDs().contains(WarmongerRunMod.ID))
            {
                new CoffeeDripper().instantObtain();
                new BattleStandard().instantObtain();
                AbstractDungeon.bossRelicPool.removeIf(id -> id.equals(CoffeeDripper.ID) || id.equals(BattleStandard.ID));
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

    public static boolean hasCard(CardGroup group, String cardID)
    {
        return group.group.stream().anyMatch(card -> card.cardID.equals(cardID));
    }

    public static void onGenerateCardMidcombat(AbstractCard c)
    {
        AbstractDungeon.player.relics.stream().filter(r -> r instanceof onGenerateCardMidcombatInterface).forEach(r -> ((onGenerateCardMidcombatInterface)r).onCreateCard(c));
        AbstractDungeon.player.powers.stream().filter(r -> r instanceof onGenerateCardMidcombatInterface).forEach(r -> ((onGenerateCardMidcombatInterface)r).onCreateCard(c));
        AbstractDungeon.player.hand.group.stream().filter(card -> card instanceof onGenerateCardMidcombatInterface).forEach(card -> ((onGenerateCardMidcombatInterface)card).onCreateCardCard(c));
        AbstractDungeon.player.discardPile.group.stream().filter(card -> card instanceof onGenerateCardMidcombatInterface).forEach(card -> ((onGenerateCardMidcombatInterface)card).onCreateCardCard(c));
        AbstractDungeon.player.drawPile.group.stream().filter(card -> card instanceof onGenerateCardMidcombatInterface).forEach(card -> ((onGenerateCardMidcombatInterface)card).onCreateCardCard(c));
        AbstractDungeon.getMonsters().monsters.stream().filter(mon -> !mon.isDeadOrEscaped()).forEach(m -> m.powers.stream().filter(pow -> pow instanceof onGenerateCardMidcombatInterface).forEach(pow -> ((onGenerateCardMidcombatInterface)pow).onCreateCard(c)));
        if (c instanceof onGenerateCardMidcombatInterface)
        {
            ((onGenerateCardMidcombatInterface)c).onCreateCard(c);
        }
//        cardsCreatedThisCombat++;
//        cardsCreatedThisTurn++;
    }

    public static String makeID(String id_in)
    {
        return "jedi:" + id_in;
    }

    public static AbstractRelic returnRandomCursedRelic()
    {
        if (cursedRelics.isEmpty()) return new Circlet();
        return RelicLibrary.getRelic(cursedRelics.remove(cursedRelics.size() - 1)).makeCopy();
    }
}

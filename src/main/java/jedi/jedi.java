package jedi;

import basemod.*;
import basemod.eventUtil.AddEventParams;
import basemod.eventUtil.EventUtils;
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
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AccuracyPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.CoffeeDripper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.custom.CustomMod;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import jedi.cards.CustomJediCard;
import jedi.cards.red.OneStrike;
import jedi.events.BetterSwordDojo;
import jedi.events.GuildOfFate;
import jedi.events.ShrineOfCommand;
import jedi.interfaces.onGenerateCardMidcombatInterface;
import jedi.interfaces.onObtainRelicInterface;
import jedi.modifiers.CommandCustomRun;
import jedi.modifiers.WarmongerRunMod;
import jedi.patches.JediEnums;
import jedi.potions.*;
import jedi.relics.AbstractCommand;
import jedi.relics.AbstractJediRelic;
import jedi.relics.BattleStandard;
import jedi.relics.MainCommand;
import jedi.util.ClassScanner;
import jedi.util.TextureLoader;
import jedi.util.Wiz;
import jedi.variables.JediSecondMN;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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
            PostUpdateSubscriber,
            AddCustomModeModsSubscriber,
            PostDungeonInitializeSubscriber,
            RelicGetSubscriber,
            OnStartBattleSubscriber,
            OnPlayerTurnStartSubscriber
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
    public static ArrayList<AbstractRelic> unseenRelics = new ArrayList<>();
    public static ArrayList<String> lockedRelics = new ArrayList<>();
    public static final String modID = "jedi";

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


        if (isGathererLoaded)
        {
            lesserPotionPool.add(new LesserTentacleJuice());
            lesserPotionPool.add(new LesserHolyWater());
        }

        // Events
        BaseMod.addEvent(BetterSwordDojo.ID, BetterSwordDojo.class);

        BaseMod.addEvent(new AddEventParams
                .Builder(ShrineOfCommand.ID, ShrineOfCommand.class)
                .eventType(EventUtils.EventType.SHRINE)
                .create()
        );
        BaseMod.addEvent(new AddEventParams
                .Builder(GuildOfFate.ID, GuildOfFate.class)
                .bonusCondition(() -> AbstractDungeon.actNum == 1 || Settings.isEndless)
                .eventType(EventUtils.EventType.ONE_TIME)
                .create());

        StrikeGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        poisonGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        shivGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        String[] poisons = {"poison", "venom"};
        String[] shivs = {"shiv", AccuracyPower.class.getSimpleName().toLowerCase()};

        for (AbstractCard card : CardLibrary.getAllCards())
        {
            if (card.type != AbstractCard.CardType.CURSE &&
                    (card.keywords.stream().anyMatch(kw -> Arrays.asList(GameDictionary.FROST.NAMES).contains(kw)) ||
                    ClassScanner.checkIfContainsWord(card, "frost")))
            {
                card.tags.add(JediEnums.FROST_CARD);
            }

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
                    (extraCheck || ClassScanner.checkIfContainsWords(card, poisons)))
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
                    (extraCheck || ClassScanner.checkIfContainsWords(card, shivs))
            )
        shivGroup.addToBottom(card.makeCopy());
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

        BaseMod.registerModBadge(TextureLoader.getTexture("jedi/images/badge.png"), "Jedi", "Jedi#3970", "Wat", settingsPanel);
    }

    @Override
    public void receiveEditCards()
    {
        BaseMod.addDynamicVariable(new JediSecondMN());
        new AutoAdd("jedi").packageFilter(CustomJediCard.class).setDefaultSeen(true).cards();
    }

    @Override
    public void receiveEditRelics()
    {
        new AutoAdd(modID).packageFilter(AbstractJediRelic.class).any(AbstractJediRelic.class, (info, relic) ->
        {
            BaseMod.addRelic(relic, relic.cardPool);
            if (info.seen) {
                UnlockTracker.markRelicAsSeen(relic.relicId);
            }
        });
    }

    private static String GetLocString(String locCode, String name) {
        return Gdx.files.internal("jedi/localization/" + locCode + "/" + name + ".json").readString(
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
        if (!Gdx.files.internal("jedi/localization/" + langKey + "/").exists()) return;
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
        if (!Gdx.files.internal("jedi/localization/" + langKey + "/").exists()) return;
        loadCustomStrings(CardStrings.class, GetLocString(langKey, "cardStrings"));
        loadCustomStrings(RelicStrings.class, GetLocString(langKey, "relicStrings"));
        loadCustomStrings(PotionStrings.class, GetLocString(langKey, "potionStrings"));
        loadCustomStrings(PowerStrings.class, GetLocString(langKey, "powerStrings"));
        loadCustomStrings(EventStrings.class, GetLocString(langKey, "eventStrings"));
        loadCustomStrings(UIStrings.class, GetLocString(langKey, "uiStrings"));
        loadCustomStrings(RunModStrings.class, GetLocString(langKey, "runmodStrings"));
    }


    public static AbstractCard returnTrulyRandomStrike()
    {
        AbstractCard check = StrikeGroup.getRandomCard(true).makeCopy(); //make it ultra rare
        if (check.cardID.equals(OneStrike.ID)) check = StrikeGroup.getRandomCard(true).makeCopy();
        return check;
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
                break;
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
        p.relics.stream().
                filter(r -> r instanceof onObtainRelicInterface).
                forEach(r -> ((onObtainRelicInterface) r).onObtainRelic(abstractRelic));
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
        Wiz.cardsCreatedThisTurn.add(c);
        Wiz.cardsCreatedThisCombat.add(c);
    }

    public static String makeID(String id_in)
    {
        return "jedi:" + id_in;
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        Wiz.cardsCreatedThisCombat.clear();
        Wiz.cardsCreatedThisTurn.clear();
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        Wiz.cardsCreatedThisTurn.clear();
    }

    public static boolean isCommandRun()
    {
        return AbstractDungeon.player.hasRelic(MainCommand.ID) || ((CardCrawlGame.trial != null) && (CardCrawlGame.trial.dailyModIDs().contains(CommandCustomRun.ID)));
    }
}

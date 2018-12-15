package sts_jedi;

import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.localization.PowerStrings;
import mod.jedi.cards.blue.*;
import mod.jedi.cards.colorless.Cleanse;
import mod.jedi.cards.colorless.Forcepull;
import mod.jedi.cards.colorless.Forcepush;
import mod.jedi.potions.CoolantLeak;
import mod.jedi.potions.HolyWater;
import mod.jedi.potions.TentacleJuice;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import basemod.BaseMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import mod.jedi.relics.*;

import static basemod.BaseMod.isBaseGameCharacter;
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

    public static void initialize()
    {
        BaseMod.subscribe(new jedi());
        isReplayLoaded = Loader.isModLoaded("ReplayTheSpireMod"); // this String is the modid from ModTheSpire.json of the mod you want to find

    }

//    		BaseMod.addPotion(potionClass, liquidColor, hybridColor, spotsColor, potionID);

    @Override
    public void receivePostInitialize()
    {
        BaseMod.addPotion(TentacleJuice.class, Color.PURPLE,null,Color.WHITE,TentacleJuice.ID);
        BaseMod.addPotion(CoolantLeak.class, null, Color.CYAN, null,CoolantLeak.ID, AbstractPlayer.PlayerClass.DEFECT);
        BaseMod.addPotion(HolyWater.class, Color.YELLOW, Color.WHITE, null, HolyWater.ID);
    }

    @Override
    public void receiveEditCards()
    {
        BaseMod.addCard(new Cleanse());
        BaseMod.addCard(new Forcepush());
        BaseMod.addCard(new Forcepull());
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
    }

    @Override
    public void receiveEditRelics()
    {
        BaseMod.addRelic(new Matchstick(), RelicType.SHARED);
        BaseMod.addRelic(new HotPepper(), RelicType.SHARED);
        BaseMod.addRelic(new LaserPointer(), RelicType.BLUE);
        BaseMod.addRelic(new FakeMustache(), RelicType.SHARED);
        BaseMod.addRelic(new Endoplasm(), RelicType.SHARED);
        BaseMod.addRelic(new Superconductor(), RelicType.BLUE);
        BaseMod.addRelic(new LeadLinedBottle(), RelicType.SHARED);

//        if (isReplayLoaded) {
//            BaseMod.addRelic(new OtherSneckoEye(), RelicType.SHARED);
//        }
    }

    @Override
    public void receiveEditStrings() {
        loadCustomStringsFile(CardStrings.class,"resources/localization/cardStrings.json");
        loadCustomStringsFile(RelicStrings.class, "resources/localization/relicStrings.json");
        loadCustomStringsFile(PotionStrings.class, "resources/localization/potionStrings.json");
        loadCustomStringsFile(PowerStrings.class, "resources/localization/powerStrings.json");
    }

    @Override
    public int receiveMapHPChange(int amount) {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(HotPepper.ID)) {
            HotPepper relic = (HotPepper) AbstractDungeon.player.getRelic(HotPepper.ID);
            amount = relic.onMaxHPChange(amount);
        }
        return amount;
    }

}

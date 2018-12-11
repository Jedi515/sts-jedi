package sts_jedi;

import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.jedi.cards.blue.*;
import com.evacipated.cardcrawl.mod.jedi.cards.colorless.Cleanse;
import com.evacipated.cardcrawl.mod.jedi.cards.colorless.Forcepull;
import com.evacipated.cardcrawl.mod.jedi.cards.colorless.Forcepush;
import com.evacipated.cardcrawl.mod.jedi.potions.CoolantLeak;
import com.evacipated.cardcrawl.mod.jedi.potions.HolyWater;
import com.evacipated.cardcrawl.mod.jedi.potions.TentacleJuice;
import com.evacipated.cardcrawl.mod.jedi.relics.HotPepper;
import com.evacipated.cardcrawl.mod.jedi.relics.Matchstick;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import basemod.BaseMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;

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

    public static void initialize()
    {
        BaseMod.subscribe(new jedi());
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
        loadCustomStringsFile(PotionStrings.class, "resources/localization/potionStrings.json");
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

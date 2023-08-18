package jedi.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import jedi.cards.curses.BattleThirst;
import jedi.jedi;
import jedi.relics.*;

public class ShrineOfCommand
    extends AbstractImageEvent
{
    public static final String ID = jedi.makeID(ShrineOfCommand.class.getSimpleName());
    public static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String NAME = eventStrings.NAME;
    public int mHPLoss;
    public int hpLoss;


    public ShrineOfCommand()
    {
        super(NAME, DESCRIPTIONS[0], "jedi/images/events/ShrineOfCommand.jpg");
        imageEventText.setDialogOption(DESCRIPTIONS[2]);
        imageEventText.setDialogOption(DESCRIPTIONS[3]);
    }

    @Override
    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
            case 0:
            {
                switch(buttonPressed)
                {
                    case 0:
                    {
                        imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        imageEventText.clearRemainingOptions();
                        if (AbstractDungeon.ascensionLevel > 14)
                        {
                            mHPLoss = (int) (AbstractDungeon.player.maxHealth * 0.4);
                            hpLoss = 20;
                        }
                        else
                        {
                            mHPLoss = (int) (AbstractDungeon.player.maxHealth * 0.25);
                            hpLoss = 15;
                        }

                        this.imageEventText.updateDialogOption(0, String.format(OPTIONS[0], hpLoss));
                        if (AbstractDungeon.player.potionSlots > 0)
                            this.imageEventText.setDialogOption(OPTIONS[1]);
                        else
                        {
                            imageEventText.setDialogOption(OPTIONS[7], true);
                        }
                        this.imageEventText.setDialogOption(OPTIONS[2], new BattleThirst());
                        this.imageEventText.setDialogOption(String.format(OPTIONS[3], mHPLoss));
                        if (AbstractDungeon.player.gold > 199)
                            this.imageEventText.setDialogOption(OPTIONS[4]);
                        else
                            imageEventText.setDialogOption(OPTIONS[8], true);
                        imageEventText.setDialogOption(OPTIONS[5]);
                        screenNum++;
                        return;
                    }
                    case 1:
                        screenNum = 2;
                        imageEventText.updateDialogOption(0, OPTIONS[6]);
                        imageEventText.clearRemainingOptions();
                        return;
                }
            }
            case 1:
            {
                switch (buttonPressed)
                {
                    case 0:
                        AbstractDungeon.player.damage(new DamageInfo(null, hpLoss, DamageInfo.DamageType.HP_LOSS));
                        new Command_common().instantObtain();
                        break;
                    case 1:
                        AbstractDungeon.player.potionSlots--;
                        AbstractDungeon.player.potions.remove(AbstractDungeon.player.potions.size() - 1);
                        new Command_uncommon().instantObtain();
                        break;
                    case 2:
                        AbstractCard curse = new BattleThirst();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float) (Settings.WIDTH / 2), (float) (Settings.HEIGHT / 2)));
                        new Command_rare().instantObtain();
                        break;
                    case 3:
                        AbstractDungeon.player.decreaseMaxHealth(mHPLoss);
                        new Command_boss().instantObtain();
                        break;
                    case 4:
                        AbstractDungeon.player.loseGold(AbstractDungeon.player.gold);
                        new Command_shop().instantObtain();
                        break;
                    case 5:
                        AbstractDungeon.player.damage(new DamageInfo(null, Integer.MAX_VALUE, DamageInfo.DamageType.HP_LOSS));
                        new MainCommand().instantObtain();
                        break;
                }
                screenNum = 2;
                imageEventText.updateDialogOption(0, OPTIONS[6]);
                imageEventText.clearRemainingOptions();
                return;
            }
            default:
                openMap();
        }
    }
}

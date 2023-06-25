package jedi.events;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton;
import jedi.jedi;
import jedi.relics.TokenOfGlory;
import jedi.relics.TokenOfMystery;
import jedi.relics.TokenOfSerenity;
import jedi.relics.TokenOfWealth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GuildOfFate
        extends AbstractImageEvent
    {
        public static final String ID = jedi.makeID(GuildOfFate.class.getSimpleName());
        public static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
        public static final String[] OPTIONS = eventStrings.OPTIONS;
        public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        public static final String NAME = eventStrings.NAME;

    public GuildOfFate()
        {
            super(NAME, String.format(DESCRIPTIONS[0], FontHelper.colorString(new SimpleDateFormat("HH:mm dd.MM.yyyy").format(new Date()), "y")), "resources/jedi/images/events/mausoleum.jpg");
            imageEventText.setDialogOption(OPTIONS[0]);
            imageEventText.setDialogOption(OPTIONS[1]);
        }

        @Override
        protected void buttonEffect(int buttonPressed)
        {
            switch (screenNum)
            {
                case 0:
                    switch (buttonPressed)
                    {
                        case 0:
                            imageEventText.updateBodyText(DESCRIPTIONS[1]);
                            imageEventText.clearRemainingOptions();
                            imageEventText.optionList.set(0, new LargeDialogOptionButton(0, OPTIONS[2] + OPTIONS[6], new TokenOfGlory()));
                            imageEventText.setDialogOption(OPTIONS[3] + OPTIONS[6], new TokenOfMystery());
                            imageEventText.setDialogOption(OPTIONS[4] + OPTIONS[6], new TokenOfSerenity());
                            imageEventText.setDialogOption(OPTIONS[5] + OPTIONS[6], new TokenOfWealth());
                            screenNum++;
                            return;
                        case 1:
                            screenNum = 2;
                            imageEventText.updateBodyText(DESCRIPTIONS[2]);
                            imageEventText.updateDialogOption(0, OPTIONS[6]);
                            imageEventText.clearRemainingOptions();
                            return;
                    }
                    return;
                case 1:
                    switch (buttonPressed)
                    {
                        case 0:
                            new TokenOfGlory().instantObtain();
                            break;
                        case 1:
                            new TokenOfMystery().instantObtain();
                            break;
                        case 2:
                            new TokenOfSerenity().instantObtain();
                            break;
                        case 3:
                            new TokenOfWealth().instantObtain();
                    }
                    screenNum++;
                    imageEventText.updateDialogOption(0, OPTIONS[7]);
                    imageEventText.clearRemainingOptions();
                    openMap();
                    return;
                default:
                    openMap();
            }
        }
    }

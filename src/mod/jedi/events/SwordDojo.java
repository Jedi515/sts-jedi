package mod.jedi.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.PerfectedStrike;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static sts_jedi.jedi.returnTrulyRandomStrike;

public class SwordDojo
    extends AbstractImageEvent
{
//    cardLibrary.getAllCards()
//    for (Map.Entry<String, AbstractCard> entrySetCard : CardLibrary.cards.entrySet()) {
//    AbstractCard card = entrySetCard.getValue();
//    //check against card
//      }

    public static final String ID = "jedi:sworddojo";
    public static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    public static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String NAME = eventStrings.NAME;
    private static int strikesAmount;
    private int screenNum = 0;


    public SwordDojo()
    {
        super(NAME, DESCRIPTIONS[0], "resources/images/events/mausoleum.jpg");
        if (AbstractDungeon.ascensionLevel >= 15)
        {
            strikesAmount = 3;
        }
        else
        {
            strikesAmount = 5;
        }
        this.imageEventText.setDialogOption(OPTIONS[0] + strikesAmount + OPTIONS[1]);
        this.imageEventText.setDialogOption(OPTIONS[2]);
        this.imageEventText.setDialogOption(OPTIONS[3]);
    }


    @Override
    protected void buttonEffect(int buttonPressed) {
        switch (this.screenNum) {
            case 0:
                switch (buttonPressed) {
                    case 0:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[1]);
                        screenNum = 1;

                        CardGroup GiveToPlayer = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                        for (int i = 0; i < strikesAmount; i++)
                        {
                            AbstractCard card = returnTrulyRandomStrike();
                            GiveToPlayer.addToBottom(card.makeCopy());
                            UnlockTracker.markCardAsSeen(card.cardID);
                            card.isSeen = true;
                        }
                        GiveToPlayer.addToBottom(new PerfectedStrike());
                        AbstractDungeon.gridSelectScreen.openConfirmationGrid(GiveToPlayer, this.DESCRIPTIONS[4]);
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    case 1:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[2]);
                        screenNum = 1;
                        AbstractCard curse = AbstractDungeon.returnRandomCurse().makeCopy();
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float)(Settings.WIDTH / 2), (float)(Settings.HEIGHT / 2)));
                        AbstractRelic r = AbstractDungeon.returnRandomScreenlessRelic(AbstractDungeon.returnRandomRelicTier());
                        AbstractDungeon.combatRewardScreen.open();
                        AbstractDungeon.getCurrRoom().rewards.clear();
                        AbstractDungeon.getCurrRoom().addRelicToRewards(r);
                        AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                    default:
                        this.imageEventText.updateBodyText(DESCRIPTIONS[3]);
                        screenNum = 1;
                        this.imageEventText.updateDialogOption(0, OPTIONS[3]);
                        this.imageEventText.clearRemainingOptions();
                        return;
                }
            default:
                this.openMap();
        }
    }
}

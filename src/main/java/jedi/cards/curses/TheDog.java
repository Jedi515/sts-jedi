package jedi.cards.curses;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.AlwaysRetainField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import jedi.cards.CustomJediCard;

public class TheDog
    extends CustomJediCard
{
    public static final String ID = makeCardId("thedog");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = -2;

    public TheDog()
    {
        super(ID, NAME, COST, DESCRIPTION, CardType.CURSE, CardColor.CURSE, CardRarity.CURSE, CardTarget.NONE);
        AlwaysRetainField.alwaysRetain.set(this, true);
    }

    public void theDogStays()
    {
        AbstractDungeon.effectList.add(new SpeechBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, UPGRADE_DESCRIPTION, true));
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {


    }

    public void upgrade()
    {
    }
}

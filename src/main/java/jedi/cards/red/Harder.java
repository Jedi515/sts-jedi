package jedi.cards.red;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.MetallicizePower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import jedi.cards.CustomJediCard;

public class Harder
    extends CustomJediCard
{
    public static final String ID = "jedi:harder";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String UPGRADE_NAME = "Work it ";
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 1;


    public Harder()
    {
        super(ID, NAME, COST, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.RARE, CardTarget.SELF);
        magicNumber = baseMagicNumber = 2;
        cardsToPreview = new Better();
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        Better better = new Better();
        if (upgraded)
        {
            better.upgrade();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p, magicNumber), magicNumber));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, magicNumber), magicNumber));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(better));
    }

    @Override
    public void upgrade()
    {
        if (!upgraded) {
            cardsToPreview.upgrade();
            upgradeName();
            name = UPGRADE_NAME+NAME;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}

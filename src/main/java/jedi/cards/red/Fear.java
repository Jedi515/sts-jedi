package jedi.cards.red;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.red.Anger;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.cards.CustomJediCard;
import jedi.powers.FearPower;
import jedi.util.Wiz;

public class Fear
    extends CustomJediCard
{
    public static final String ID = "jedi:fear";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 1;


    public Fear()
    {
        super(ID, NAME, COST, DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = 1;
    }


    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new ApplyPowerAction(Wiz.adp(), Wiz.adp(), new FearPower(Wiz.adp(), magicNumber)));
        if (upgraded) addToBot(new MakeTempCardInHandAction(new Anger()));
    }

    @Override
    public void upp()
    {
        cardsToPreview = new Anger();
    }
}

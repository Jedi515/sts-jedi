package jedi.cards.purple;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.DivinityStance;
import jedi.actions.ScryCallbackAction;
import jedi.cards.CustomJediCard;
import jedi.jedi;
import jedi.util.Wiz;

import java.util.HashSet;
import java.util.Set;

public class ApocalypseNow
    extends CustomJediCard
{
    public static String ID = jedi.makeID(ApocalypseNow.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public ApocalypseNow()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.SKILL, AbstractCard.CardColor.PURPLE, AbstractCard.CardRarity.RARE, CardTarget.NONE);
        setBlock(6);
        setMN(10);
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster abstractMonster)
    {
        Wiz.doBlk(block);
        addToBot(new ScryCallbackAction(magicNumber, list ->
        {
            Set<CardType> set = new HashSet<>();
            list.forEach(c -> set.add(c.type));
            if (set.size() > 2) addToBot(new ChangeStanceAction(DivinityStance.STANCE_ID));
        }));
    }
}

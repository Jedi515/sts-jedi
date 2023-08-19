package jedi.cards.blue;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import jedi.cardMods.BurstDarkMod;
import jedi.cards.CustomJediCard;
import jedi.interfaces.onEvokeInterface;


public class BurstDark
        extends CustomJediCard
    implements onEvokeInterface
{
    public static final String ID = "jedi:BurstDark";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private final BurstDarkMod burstDarkMod;

    public BurstDark()
    {
        super(ID, NAME, 2, EXTENDED_DESCRIPTION[0] + DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.RARE, CardTarget.ENEMY);
        burstDarkMod = new BurstDarkMod(0);
        selfRetain = true;
        CardModifierManager.addModifier(this, burstDarkMod);
        magicNumber = baseMagicNumber = 5;
        damage = baseDamage = 11;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.orbsChanneledThisCombat.add(new Dark());
    }

    public void upp()
    {
        upgradeMagicNumber(3);
    }

    @Override
    public void onEvoke(AbstractOrb orb)
    {
        burstDarkMod.amount += magicNumber;
        applyPowers();
    }

    public AbstractCard makeCopy()
    {
        AbstractCard copy = super.makeCopy();
        ((BurstDark)copy).burstDarkMod.amount = burstDarkMod.amount;
        return copy;
    }
}

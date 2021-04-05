package mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import basemod.interfaces.PostEnergyRechargeSubscriber;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
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
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import mod.jedi.cardMods.DamageUpMod;
import mod.jedi.cards.CustomJediCard;
import mod.jedi.interfaces.onEvokeInterface;


public class BurstDark
        extends CustomJediCard
    implements onEvokeInterface
{
    public static final String ID = "jedi:BurstDark";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private DamageUpMod damageUpMod;

    public BurstDark()
    {
        super(ID, NAME, "resources/jedi/images/cards/jedi_beta_attack.png", 2, DESCRIPTION, CardType.ATTACK, CardColor.BLUE, CardRarity.RARE, CardTarget.ENEMY);
        damageUpMod = new DamageUpMod(0);
        CardModifierManager.addModifier(this, damageUpMod);
        magicNumber = baseMagicNumber = 5;
        this.damage = this.baseDamage = 6;
    }

    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.orbsChanneledThisCombat.add(new Dark());
    }

    public void upgrade()
    {
        if(!this.upgraded)
        {
            upgradeName();
            selfRetain = true;
            rawDescription = EXTENDED_DESCRIPTION[0] + rawDescription;
            initializeDescription();
        }
    }

    @Override
    public void onEvoke(AbstractOrb orb)
    {
        damageUpMod.amount += magicNumber;
        applyPowers();
    }

    public AbstractCard makeCopy()
    {
        AbstractCard copy = super.makeCopy();
        ((BurstDark)copy).damageUpMod.amount = damageUpMod.amount;
        return copy;
    }
}

package mod.jedi.cards.blue;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import java.util.Collections;

public class Bruteforce
    extends CustomCard
{
    public static final String ID = "jedi:bruteforce";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 2;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta.png";
    private int hitsTillBreak;
    private DamageInfo info;

    public Bruteforce()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.RARE, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = 2;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.hitsTillBreak = 0;
        this.info = evokeInfo();
        while (this.hitsTillBreak < this.magicNumber || (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()))
        {
            AbstractCreature mo = AbstractDungeon.getRandomMonster();
            if (m == mo)
            {
                this.hitsTillBreak++;
            }
            if (mo != null) {
                float speedTime = 0.2F / (float)AbstractDungeon.player.orbs.size();
                if (Settings.FAST_MODE) {
                    speedTime = 0.0F;
                }

                this.info.output = AbstractOrb.applyLockOn(mo, this.info.base);
                AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, this.info, AbstractGameAction.AttackEffect.NONE, true));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), speedTime));
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_EVOKE"));
            }

        }

        for (AbstractOrb o : AbstractDungeon.player.orbs)
        {
            if (o instanceof Lightning)
            {
                int position = AbstractDungeon.player.orbs.indexOf(o);
                AbstractOrb orbSlot = new EmptyOrbSlot();

                int i;
                for(i = position + 1; i < AbstractDungeon.player.orbs.size(); ++i) {
                    Collections.swap(AbstractDungeon.player.orbs, i, i - 1);
                }

                AbstractDungeon.player.orbs.set(AbstractDungeon.player.orbs.size() - 1, orbSlot);

                for(i = position; i < AbstractDungeon.player.orbs.size(); ++i) {
                    ((AbstractOrb)AbstractDungeon.player.orbs.get(i)).setSlot(i, AbstractDungeon.player.maxOrbs);
                }
                return;
            }
        }
    }

    //            AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageType.THORNS), false));

    private DamageInfo evokeInfo()
    {
        return new DamageInfo(AbstractDungeon.player, evokeAmt(), DamageInfo.DamageType.THORNS);
    }

    private int evokeAmt()
    {
        for (AbstractOrb o : AbstractDungeon.player.orbs)
        {
            if (o instanceof Lightning)
            {
                return o.evokeAmount;
            }
        }
        return 0;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        boolean canUse = false;
        for (AbstractOrb o : AbstractDungeon.player.orbs) {
            if (o instanceof Lightning) {
                canUse = true;
            }
        }
        if (!canUse)
        {
            this.cantUseMessage = UPGRADE_DESCRIPTION;
        }

        return canUse;
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(1);
        }
    }
}

package mod.jedi.cards.red;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import mod.jedi.cards.AbstractCollectorCard;

public class CollectorStrike
    extends AbstractCollectorCard
{
    public static final String ID = "jedi:collectorstrike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final int COST = 1;
    public static final String IMG_PATH = "resources/jedi/images/cards/jedi_beta_attack.png";

    public CollectorStrike()
    {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, CardColor.RED, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = this.misc = 3;
        this.magicNumber = this.baseMagicNumber = 3;
        this.tags.add(CardTags.STRIKE);
    }
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage), AttackEffect.FIRE));
    }

    public void onAddedToMasterDeck()
    {
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group)
        {
            if (card.getClass() == this.getClass() && card != this)
            {
                card.misc += card.magicNumber;
                card.damage = card.baseDamage = card.misc;
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShowCardBrieflyEffect(card)));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new UpgradeShineEffect(Settings.WIDTH/2, Settings.HEIGHT/2)));
                AbstractDungeon.player.masterDeck.removeCard(this);
                break;
            }
        }
    }

    public void applyPowers() {
        this.baseDamage = this.misc;
        super.applyPowers();
        this.initializeDescription();
    }

    public void upgrade()
    {
        if (!this.upgraded)
        {
            this.upgradeMagicNumber(1);
            this.upgradeName();
        }
    }
}

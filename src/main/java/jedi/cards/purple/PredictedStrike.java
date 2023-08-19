package jedi.cards.purple;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import jedi.actions.ScryCallbackAction;
import jedi.actions.ScrySeenCallbackAction;
import jedi.cardMods.DamageUpMod;
import jedi.cards.CustomJediCard;

public class PredictedStrike
    extends CustomJediCard
{
    public static final String ID = makeCardId(PredictedStrike.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final int COST = 1;

    public PredictedStrike()
    {
        super(ID, NAME, null, COST, DESCRIPTION, CardType.ATTACK, CardColor.PURPLE, CardRarity.UNCOMMON, CardTarget.ENEMY);
        tags.add(CardTags.STRIKE);
        setDmg(7);
        setMN(3);
    }

    @Override
    public void upgrade()
    {
        if (!upgraded)
        {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
        addToBot(new ScrySeenCallbackAction(magicNumber, list -> list.stream().filter(c -> c.type == CardType.ATTACK).forEach(c -> {
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(c.target_x, c.target_y));
            CardModifierManager.addModifier(c, new DamageUpMod(magicNumber));
            c.applyPowers();
        })));
    }
}

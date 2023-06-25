package jedi.cardMods.gathererEssenceMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SingleDamageMod
    extends AbstractEssenceMod
{
    public SingleDamageMod(int damage)
    {
        baseValue = damage;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        return TEXT[0] + rawDescription;
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new SingleDamageMod(baseValue);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action)
    {
        addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }
}

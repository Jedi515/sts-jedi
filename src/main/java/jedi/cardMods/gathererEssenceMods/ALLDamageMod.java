package jedi.cardMods.gathererEssenceMods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ALLDamageMod
    extends AbstractEssenceMod
{
    public int[] damage;
    public boolean modified;
    public boolean upgraded;

    ALLDamageMod(int damage)
    {
        baseValue = damage;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card)
    {
        return TEXT[2] + rawDescription;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action)
    {
        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public AbstractCardModifier makeCopy()
    {
        return new ALLDamageMod(baseValue);
    }
}

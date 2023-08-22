package jedi.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import jedi.jedi;

@AutoAdd.Ignore
public abstract class CustomJediCard
    extends CustomCard
{
    public int secondMN;
    public int baseSecondMN;
    public boolean isSecondMNUpgraded;
    public boolean isSecondMNModified;

    public static String makeCardId(String ID)
    {
        return "jedi:" + ID;
    }

    public CustomJediCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target)
    {
        super(id, name, imgCheck(id, img, type, color), cost, rawDescription, type, color, rarity, target);
        isSecondMNModified = false;
    }

    public CustomJediCard(String id, String name, int cost, String rawDescription, CardType type, CardColor color, CardRarity rarity, CardTarget target)
    {
        super(id, name, imgCheck(id, null, type, color), cost, rawDescription, type, color, rarity, target);
        isSecondMNModified = false;
    }

    private static String imgCheck(String id, String img, CardType type, CardColor color)
    {
        if ((img != null) && Gdx.files.internal(img).exists()) return img;

        String imgCheck = "jedi/images/cards/" + color.toString().toLowerCase() + "/" + type.toString().toLowerCase() + "/" + id.substring(5) + ".png";
        if (Gdx.files.internal(imgCheck).exists()) return imgCheck;

        switch (type)
        {
            case ATTACK:
                img = "jedi/images/cards/jedi_beta_attack.png";
                break;
            case POWER:
                img = "jedi/images/cards/jedi_beta_power.png";
                break;
            default:
                img = "jedi/images/cards/jedi_beta.png";
                break;
        }
        return img;
    }

    public void displayUpgrades()
    {
        super.displayUpgrades();
        if (isSecondMNUpgraded)
        {
            secondMN = baseSecondMN;
            isSecondMNModified = true;
        }
    }

    public void upgradeSecondMN(int amount)
    {
        baseSecondMN += amount;
        secondMN = baseSecondMN;
        isSecondMNUpgraded = true;
    }

    protected void setDmg(int value)
    {
        baseDamage = damage = value;
    }

    protected void setBlock(int value)
    {
        baseBlock = block = value;
    }

    protected void setMN(int value)
    {
        baseMagicNumber = magicNumber = value;
    }

    protected void setSecondMN(int value)
    {
        baseSecondMN = secondMN = value;
    }

    protected void atb(AbstractGameAction action)
    {
        addToBot(action);
    }

    protected void att(AbstractGameAction action)
    {
        addToTop(action);
    }

    protected void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx)
    {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void dmgTop(AbstractMonster m, AbstractGameAction.AttackEffect fx)
    {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void allDmg(AbstractGameAction.AttackEffect fx)
    {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void blck()
    {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    protected void draw(int amt)
    {
        atb(new DrawCardAction(amt));
    }

    protected void uDesc(String newDesc)
    {
        rawDescription = newDesc;
        initializeDescription();
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
        }
    }

    protected void upp(){}

    protected String makeID(String cardID)
    {
        return jedi.makeID(cardID);
    }
}
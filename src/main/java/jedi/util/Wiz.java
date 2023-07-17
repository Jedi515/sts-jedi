package jedi.util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Wiz {

    public static void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
    public static AbstractPlayer adp() {
        return AbstractDungeon.player;
    }

    public static AbstractPlayer p() {
        return AbstractDungeon.player;
    }

    public static CardGroup hand() {
        return AbstractDungeon.player.hand;
    }

    public static CardGroup drawPile() {
        return AbstractDungeon.player.drawPile;
    }

    public static CardGroup discardPile() {
        return AbstractDungeon.player.discardPile;
    }

    public static CardGroup deck() {
        return AbstractDungeon.player.masterDeck;
    }

    public static void forAllCardsInList(Consumer<AbstractCard> consumer, ArrayList<AbstractCard> cardsList) {
        for (AbstractCard c : cardsList) {
            consumer.accept(c);
        }
    }

    public static int getLogicalPowerAmount(AbstractCreature ac, String powerId) {
        AbstractPower pow = ac.getPower(powerId);
        if (pow == null) return 0;
        return pow.amount;
    }

    public static ArrayList<AbstractMonster> getEnemies() {
        return AbstractDungeon.getMonsters().monsters.stream().
                filter(m -> !m.isDeadOrEscaped()).collect(Collectors.toCollection(ArrayList::new));
    }


    public static void makeInHand(AbstractCard c, int i) {
        atb(new MakeTempCardInHandAction(c, i));
    }

    public static void makeInHand(AbstractCard c) {
        makeInHand(c, 1);
    }

    public static void shuffleIn(AbstractCard c, int i) {
        atb(new MakeTempCardInDrawPileAction(c, i, true, true));
    }

    public static void shuffleIn(AbstractCard c) {
        shuffleIn(c, 1);
    }

    public static void topDeck(AbstractCard c, int i) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    public static void topDeck(AbstractCard c) {
        topDeck(c, 1);
    }

    public static void applyToEnemy(AbstractMonster m, AbstractPower po) {
        atb(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToEnemyTop(AbstractMonster m, AbstractPower po) {
        att(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToSelfTop(AbstractPower po) {
        att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public static void reducePower(AbstractPower p, int amount) {
        atb(new ReducePowerAction(p.owner, p.owner, p, amount));
    }

    public static void reducePower(AbstractPower p) {
        reducePower(p, 1);
    }

    public static void removePower(AbstractPower p, boolean top) {
        if (top) {
            att(new RemoveSpecificPowerAction(p.owner, p.owner, p));
        } else {
            atb(new RemoveSpecificPowerAction(p.owner, p.owner, p));
        }
    }

    public static void removePower(AbstractPower p) {
        removePower(p, false);
    }

    public static void doDmg(AbstractCreature target, AbstractCard c) {
        doDmg(target, c.damage, c.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE);
    }

    public static void doDmg(AbstractCreature target, AbstractCard c, AbstractGameAction.AttackEffect ae) {
        doDmg(target, c.damage, c.damageTypeForTurn, ae);
    }

    public static void doDmg(AbstractCreature target, AbstractCard c, AbstractGameAction.AttackEffect ae, boolean top) {
        doDmg(target, c.damage, c.damageTypeForTurn, ae, false, top);
    }

    public static void doDmg(AbstractCreature target, int amount) {
        doDmg(target, amount, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt) {
        doDmg(target, amount, dt, AbstractGameAction.AttackEffect.NONE);
    }

    public static void doDmg(AbstractCreature target, int amount, AbstractGameAction.AttackEffect ae) {
        doDmg(target, amount, DamageInfo.DamageType.NORMAL, ae);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt, AbstractGameAction.AttackEffect ae) {
        doDmg(target, amount, dt, ae, false);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt, AbstractGameAction.AttackEffect ae, boolean fast) {
        doDmg(target, amount, dt, ae, fast, false);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt, AbstractGameAction.AttackEffect ae, boolean fast, boolean top) {
        if (target == null) {
            target = AbstractDungeon.getRandomMonster();
        }
        if (top) {
            att(new DamageAction(target, new DamageInfo(p(), amount, dt), ae, fast));
        } else {
            atb(new DamageAction(target, new DamageInfo(p(), amount, dt), ae, fast));
        }
    }

    public static void thornDmg(AbstractCreature m, int amount, AbstractGameAction.AttackEffect AtkFX) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS), AtkFX));
    }

    public static void thornDmg(AbstractCreature m, int amount) {
        thornDmg(m, amount, AbstractGameAction.AttackEffect.NONE);
    }

    public static void thornDmgTop(AbstractCreature m, int amount, AbstractGameAction.AttackEffect AtkFX) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS), AtkFX));
    }

    public static void thornDmgTop(AbstractCreature m, int amount, AbstractGameAction.AttackEffect AtkFX, boolean superfast) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS), AtkFX, superfast));
    }

    public static void thornDmgTop(AbstractCreature m, int amount) {
        thornDmgTop(m, amount, AbstractGameAction.AttackEffect.NONE);
    }

    public static void doAllDmg(int amount, AbstractGameAction.AttackEffect ae, DamageInfo.DamageType dt, boolean top) {
        if (top) {
            att(new DamageAllEnemiesAction(p(), amount, dt, ae));
        } else {
            atb(new DamageAllEnemiesAction(p(), amount, dt, ae));
        }
    }

    public static void doAllDmg(AbstractCard c, AbstractGameAction.AttackEffect ae, boolean top) {
        if (top) {
            att(new DamageAllEnemiesAction(p(), c.multiDamage, c.damageTypeForTurn, ae));
        } else {
            atb(new DamageAllEnemiesAction(p(), c.multiDamage, c.damageTypeForTurn, ae));
        }
    }

    public static void doBlk(AbstractCard c) {
        doBlk(c.block, false);
    }

    public static void doBlk(int amount) {
        doBlk(amount, false);
    }

    public static void doBlk(int amount, boolean top) {
        if (top) {
            att(new GainBlockAction(p(), p(), amount));
        } else {
            atb(new GainBlockAction(p(), p(), amount));
        }
    }

    public static void doBlk(int amount, AbstractCreature source, boolean top) {
        if (top) {
            att(new GainBlockAction(p(), source, amount));
        } else {
            atb(new GainBlockAction(p(), source, amount));
        }
    }
}

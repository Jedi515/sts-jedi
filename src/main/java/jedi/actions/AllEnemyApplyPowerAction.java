package jedi.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import jedi.util.Wiz;

import java.util.ArrayList;
import java.util.function.Function;

public class AllEnemyApplyPowerAction extends AbstractGameAction {
    private Function<AbstractMonster, AbstractPower> compute;

    private ArrayList<ApplyPowerAction> actions = new ArrayList<>();

    public AllEnemyApplyPowerAction(AbstractCreature source, int amount, Function<AbstractMonster, AbstractPower> compute) {
        this.source = source;
        this.amount = amount;

        this.actionType = ActionType.POWER;
        this.compute = compute;

        for (AbstractMonster q : Wiz.getEnemies()) {
            actions.add(new ApplyPowerAction(q, source, compute.apply(q), amount));
        }

    }

    @Override
    public void update() {
        isDone = true;
        if (!AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            for (AbstractGameAction action : actions) {
                if (!action.isDone) {
                    action.update();
                    isDone = false;
                }
            }
        }
    }
}
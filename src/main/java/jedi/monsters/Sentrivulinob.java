package jedi.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.GremlinNob;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;
import jedi.cards.colorless.Stupefied;
import jedi.jedi;
import jedi.powers.NobVigorPower;
import jedi.util.Wiz;

public class Sentrivulinob
    extends AbstractMonster
{
    public static String ID = jedi.makeID(Sentrivulinob.class.getSimpleName());
    public static MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static String NAME = monsterStrings.NAME;
    public static String[] DIALOG = monsterStrings.DIALOG;
    public static String[] MOVES = monsterStrings.MOVES;

    private int metalStart;
    private int metalBuff;
    private int bonkDamage;
    private int shellBlock;
    private int thwackZapDamage;
    private int clawBurnDamage;
    private int strengthGain;
    private boolean asleep = true;
    private boolean bonked = false;
    private boolean firstLoop = true;
    private int turnCount = 0;
    private int idleCount = 0;


    public Sentrivulinob()
    {
        super(NAME, ID, 240, 0.0F, 0.0F, 530.0F, 470.0F, "jedi/images/monsters/sentrivulinob/1.png", 0, 0);
        type = EnemyType.BOSS;

        if (AbstractDungeon.ascensionLevel < 9) {
            setHp(240);
        } else {
            setHp(254);
        }
        metalStart = 6;
        metalBuff = 2;

        clawBurnDamage = 9;
        thwackZapDamage = 10;
        bonkDamage = 35;
        strengthGain = 2;
        shellBlock = 15;
        if (AbstractDungeon.ascensionLevel > 3)
        {
            clawBurnDamage += 2;
            thwackZapDamage += 2;
            bonkDamage += 10;
            strengthGain++;
            shellBlock += 5;
            metalStart += 2;
            metalBuff++;
        }

        if (AbstractDungeon.ascensionLevel > 18)
        {
            shellBlock += 5;
            idleCount++;
            strengthGain++;
            metalBuff++;
        }

        damage.add(new DamageInfo(this, thwackZapDamage));
        damage.add(new DamageInfo(this, clawBurnDamage));
        damage.add(new DamageInfo(this, bonkDamage));
    }

    public void usePreBattleAction() {
        if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) {
            CardCrawlGame.music.unsilenceBGM();
            AbstractDungeon.scene.fadeOutAmbiance();
            AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BOTTOM");
        }
        addToBot(new GainBlockAction(this, this, metalStart));
        addToBot(new ApplyPowerAction(this, this, new PlatedArmorPower(this, metalStart)));
    }

    private void playSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1A"));
        } else if (roll == 1) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1B"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_GREMLINNOB_1C"));
        }

    }
    @Override
    public void takeTurn()
    {
        switch (nextMove)
        {
            case 0:
                addToBot(new SFXAction("THUNDERCLAP"));
                addToBot(new VFXAction(this, new ShockWaveEffect(hb.cX, hb.cY, Color.ROYAL, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.5F));
                addToBot(new MakeTempCardInDiscardAction(new Dazed(), ++idleCount));
                if (idleCount > 1) {
                    asleep = false;
                    addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, Intent.BUFF));
                }
                    else addToBot(new SetMoveAction(this, MOVES[0], (byte)0, Intent.DEBUFF));
                return;
            case 1:
                //wake up
                playSfx();
                addToBot(new ApplyPowerAction(this, this, new NobVigorPower(this, 2)));
                break;
            case 2:
                addToBot(new TextAboveCreatureAction(this, TextAboveCreatureAction.TextType.STUNNED));
                addToBot(new SetMoveAction(this, MOVES[1], (byte) 1, Intent.BUFF));
                //stunned
                return;
            case 3:
                //bonk
                addToBot(new VFXAction(new WeightyImpactEffect(Wiz.adp().hb.cX, Wiz.adp().hb.cY)));
                addToBot(new DamageAction(Wiz.adp(), damage.get(2), AbstractGameAction.AttackEffect.NONE));
                bonked = true;
                break;
            case 4:
                addToBot(new MakeTempCardInDiscardAction(new Stupefied(), strengthGain));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthGain)));
                //succ
                break;
            case 5:
                addToBot(new GainBlockAction(this, shellBlock));
                addToBot(new SFXAction("THUNDERCLAP"));
                addToBot(new VFXAction(this, new ShockWaveEffect(hb.cX, hb.cY, Color.ROYAL, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.5F));
                addToBot(new MakeTempCardInDiscardAction(new Stupefied(), 1));
                //block
                break;
            case 6:
                addToBot(new DamageAction(Wiz.adp(), damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
                addToBot(new ApplyPowerAction(Wiz.adp(), this, new FrailPower(Wiz.adp(), 2, true)));
                //thwack
                break;
            case 7:
            default:
                addToBot(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
                addToBot(new VFXAction(new BorderFlashEffect(Color.SKY)));
                addToBot(new DamageAction(Wiz.adp(), damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new VFXAction(new SmallLaserEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, hb.cX, hb.cY), 0.1F));
                addToBot(new DamageAction(Wiz.adp(), damage.get(1), AbstractGameAction.AttackEffect.FIRE));
                //zap
                break;
        }
        if (nextMove > 0) turnCount++;
        addToBot(new RollMoveAction(this));
    }

    public void damage(DamageInfo info) {
        int previousHealth = currentHealth;
        super.damage(info);
        if (currentHealth != previousHealth && asleep) {
            setMove((byte) 2, Intent.STUN);
            createIntent();
            asleep = false;
        }
    }

    @Override
    public void die() {
        CardCrawlGame.screenShake.rumble(4.0F);
        super.die();
        onBossVictoryLogic();
    }

    @Override
    protected void getMove(int i)
    {
        if (asleep) {
            setMove(MOVES[0], (byte) 0, Intent.DEBUFF);
            return;
        }
        switch (turnCount % 5)
        {
            case 0:
                if (firstLoop || bonked)
                {
                    firstLoop = false;
                    setMove(MOVES[3], (byte) 4, Intent.STRONG_DEBUFF);
                }
                else setMove(MOVES[2], (byte) 3, Intent.ATTACK, damage.get(2).base);
                break;
            case 1:
            case 4:
                setMove(MOVES[5],(byte)7, Intent.ATTACK, damage.get(1).base, 2, true);
                break;
            case 2:
                setMove(MOVES[4], (byte)5, Intent.DEFEND_DEBUFF);
                break;
            case 3:
                setMove(MOVES[6], (byte)6, Intent.ATTACK_DEBUFF, damage.get(0).base);
                break;
        }

    }
}

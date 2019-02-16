package eatyourbeets.cards.animator;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import eatyourbeets.actions.EntomaAction;
import eatyourbeets.actions.OnTargetDeadAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Entoma extends AnimatorCard//_SavableInteger implements CustomSavable<Integer>
{
    public static final String ID = CreateFullID(Entoma.class.getSimpleName());

    private static final int ORIGINAL_DAMAGE = 7;
    private static final int ORIGINAL_MAGIC_NUMBER = 3;

    public Entoma()
    {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        Initialize(ORIGINAL_DAMAGE,0,ORIGINAL_MAGIC_NUMBER);

        AddExtendedDescription();
        SetSynergy(Synergies.Overlord);
    }

//    @Override
//    public AbstractCard makeStatEquivalentCopy()
//    {
//        AbstractCard c = super.makeStatEquivalentCopy();
//        c.baseDamage = ORIGINAL_DAMAGE + this.secondaryValue;
//        c.initializeDescription();
//
//        return c;
//    }
//
    @Override
    public void applyPowers()
    {
        super.applyPowers();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        if (m == null)
        {
            return;
        }

        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Color.SCARLET.cpy()), 0.3F));
        DamageAction damageAction = new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        AbstractDungeon.actionManager.addToBottom(new OnTargetDeadAction(m, damageAction, new EntomaAction(this)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new PoisonPower(m, p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void atTurnStart()
    {
        super.atTurnStart();

        if (this.baseDamage > 0)
        {
            AbstractDungeon.actionManager.addToBottom(new ModifyDamageAction(this.uuid, -1));
        }
    }

//    @Override
//    public void upgrade()
//    {
//        if (TryUpgrade())
//        {
//            upgradeMagicNumber(4);
//        }
//    }
//
//    @Override
//    protected void SetValue(Integer integer)
//    {
//        super.SetValue(integer);
//        this.baseDamage = ORIGINAL_DAMAGE + this.secondaryValue;
//    }

    @Override
    public boolean canUpgrade()
    {
        return true;
    }

    @Override
    public void upgrade()
    {
        this.timesUpgraded += 1;

        this.baseDamage = ORIGINAL_DAMAGE + timesUpgraded;
        this.upgradedDamage = true;

        this.baseMagicNumber = ORIGINAL_MAGIC_NUMBER + (timesUpgraded / 3);
        this.magicNumber = this.baseMagicNumber;
        this.upgradedMagicNumber = true;

        this.upgraded = true;
        this.name = cardStrings.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();
    }
}
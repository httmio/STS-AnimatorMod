package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;
import eatyourbeets.GameActionsHelper;
import eatyourbeets.actions.*;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

import java.util.ArrayList;

public class DwarfShaman extends AnimatorCard
{
    public static final String ID = CreateFullID(DwarfShaman.class.getSimpleName());

    public DwarfShaman()
    {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);

        Initialize(9, 0,1);
        AddExtendedDescription();
        SetSynergy(Synergies.GoblinSlayer);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        DamageAction damageAction = new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        OnTargetBlockBreakAction action = new OnTargetBlockBreakAction(m, damageAction, new DrawAndUpgradeCardAction(p, this.magicNumber));

        GameActionsHelper.Special(action);
    }

    @Override
    public void upgrade()
    {
        if (TryUpgrade())
        {
            upgradeDamage(3);
            upgradeMagicNumber(1);
        }
    }

//
//    private void OnCardsDraw(Object context, ArrayList<AbstractCard> cards)
//    {
//        if (cards.size() != 1)
//        {
//            return;
//        }
//
//        AbstractCard c = cards.get(0);
//        if (c.canUpgrade())
//        {
//            c.upgrade();
//            c.superFlash();
//            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect(c.target_x, c.target_y));
//        }
//    }
}
package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.GameActionsHelper;
import eatyourbeets.actions.ShuffleRandomGoblinAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class GoblinSlayer extends AnimatorCard
{
    public static final String ID = CreateFullID(GoblinSlayer.class.getSimpleName());

    public GoblinSlayer()
    {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);

        Initialize(4,8);

        this.retain = true;
        AddExtendedDescription();
        SetSynergy(Synergies.GoblinSlayer);
    }

    @Override
    public void atTurnStart()
    {
        super.atTurnStart();
        GameActionsHelper.Special(new ShuffleRandomGoblinAction(1));
        this.retain = true;
    }

    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp)
    {
        return super.calculateModifiedCardDamage(player, mo, tmp + (AbstractDungeon.player.exhaustPile.size() * 2));
    }

//    @Override
//    public void applyPowers()
//    {
//        this.baseDamage = BASE_DAMAGE + (AbstractDungeon.player.exhaustPile.size() * 2);
//        super.applyPowers();
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        GameActionsHelper.DamageTarget(p, m, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        GameActionsHelper.GainBlock(p, this.block);

        //int exhausted = 0;
        for (AbstractCard c : p.discardPile.group)
        {
            if (c.type == CardType.STATUS || c.type == CardType.CURSE)
            {
                GameActionsHelper.Special(new ExhaustSpecificCardAction(c, p.discardPile));
                //exhausted += 1;
            }
        }

        for (AbstractCard c : p.hand.group)
        {
            if (c.type == CardType.STATUS || c.type == CardType.CURSE)
            {
                GameActionsHelper.Special(new ExhaustSpecificCardAction(c, p.hand));
                //exhausted += 1;
            }
        }
//
//        if (exhausted > 0)
//        {
//            GameActionsHelper.Special(new ModifyDamageAction(this.uuid, exhausted * 3));
//            GameActionsHelper.Special(new ModifyBlockAction(this.uuid, exhausted * 2));
//        }
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            upgradeDamage(3);
            upgradeBlock(5);
        }
    }
}
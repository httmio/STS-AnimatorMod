package eatyourbeets.cards.animator;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import eatyourbeets.Utilities;
import eatyourbeets.actions.VariableDiscardAction;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

public class Layla extends AnimatorCard
{
    public static final String ID = CreateFullID(Layla.class.getSimpleName());

    public Layla()
    {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);

        Initialize(4,0, 3);

        AddExtendedDescription();
        SetSynergy(Synergies.Chaika);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        AbstractDungeon.actionManager.addToBottom(new VariableDiscardAction(p, BaseMod.MAX_HAND_SIZE, m, this::OnDiscard));

        initializeDescription();
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            upgradeDamage(1);
        }
    }

    private void OnDiscard(Object state, int discarded)
    {
        AbstractMonster m = Utilities.SafeCast(state, AbstractMonster.class);
        if (state == null || discarded <= 0)
        {
            return;
        }

        for (int i = 0; i < discarded; i++)
        {
            DamageInfo info = new DamageInfo(AbstractDungeon.player, this.damage, upgraded ? DamageInfo.DamageType.HP_LOSS : this.damageTypeForTurn);
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, info, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }
}
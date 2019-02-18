package eatyourbeets.cards.animator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import eatyourbeets.GameActionsHelper;
import eatyourbeets.Utilities;
import eatyourbeets.cards.AnimatorCard;
import eatyourbeets.cards.Synergies;

import java.util.ArrayList;

public class Viivi extends AnimatorCard
{
    public static final String ID = CreateFullID(Viivi.class.getSimpleName());

    public Viivi()
    {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        Initialize(3,0, 3);

        SetSynergy(Synergies.Chaika);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) 
    {
        OhMyGodJAVA enemiesHit = new OhMyGodJAVA();
        for (int i = 0; i < magicNumber; i++)
        {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new DaggerSprayEffect(AbstractDungeon.getMonsters().shouldFlipVfx()), 0.0F));
            GameActionsHelper.DamageRandomEnemy(p, this.damage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_VERTICAL, this::OnDamage, enemiesHit);
        }
    }

    @Override
    public void upgrade() 
    {
        if (TryUpgrade())
        {
            upgradeMagicNumber(1);
        }
    }

    private void OnDamage(Object state, AbstractCreature enemy)
    {
        OhMyGodJAVA enemiesHit = Utilities.SafeCast(state, OhMyGodJAVA.class);
        if (enemiesHit != null && !enemiesHit.Contains(enemy))
        {
            enemiesHit.Add(enemy);
            GameActionsHelper.ApplyPower(AbstractDungeon.player, enemy, new VulnerablePower(enemy, 1, false), 1);
        }
    }

    private class OhMyGodJAVA
    {
        private final ArrayList<AbstractCreature> list = new ArrayList<>();

        private boolean Contains(AbstractCreature creature)
        {
            return list.contains(creature);
        }

        private void Add(AbstractCreature creature)
        {
            list.add(creature);
        }
    }
}
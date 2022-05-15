package org.dragonitemc.dragoneconomy.hook.dshop;

import org.bukkit.entity.Player;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragonshop.api.RewardTask;

import javax.inject.Inject;

public class WRLDReward extends RewardTask<Double> {

    @Inject
    private NFTokenService tokenService;

    public WRLDReward() {
        super("wrld");
    }


    @Override
    public void giveReward(Double price, Player player) {
        tokenService.depositToken(player, price, "&eDragonShop 的交易");
    }
}

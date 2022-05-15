package org.dragonitemc.dragoneconomy.hook.dshop;

import org.bukkit.entity.Player;
import org.dragonitemc.dragoneconomy.api.NFTokenService;
import org.dragonitemc.dragonshop.api.RewardTask;

public class WRLDReward extends RewardTask<Object> {

    private final NFTokenService tokenService;

    public WRLDReward(NFTokenService tokenService) {
        super("wrld");
        this.tokenService = tokenService;
    }


    @Override
    public void giveReward(Object c, Player player) {
        var price = GemsPrice.toDouble(c, player);
        tokenService.depositToken(player, price, "&eDragonShop 的交易");
    }
}

package org.dragonitemc.dragoneconomy.config;

import com.ericlam.mc.eld.annotations.Prefix;
import com.ericlam.mc.eld.annotations.Resource;
import com.ericlam.mc.eld.components.LangConfiguration;
import org.dragonitemc.dragoneconomy.api.UpdateResult;

@Prefix(path = "prefix")
@Resource(locate = "lang.yml")
public class DragonEconomyMessage extends LangConfiguration {

    public String getResultMessage(UpdateResult result){
        return getLang().get("result." + result.name());
    }

    public String getErrorMessage(Throwable e) {
        e.printStackTrace();
        return getLang().get("result.ERROR", e.getMessage());
    }

    public String getDisplay(String currency, Object amount){
        return getLang().getPure("display."+currency, amount);
    }


    public String getBalanceDisplay(String player, String currency, Object amount){
        return getLang().get("balance", player, getLang().getPure("display."+currency, amount));
    }
}

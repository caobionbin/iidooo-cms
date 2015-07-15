package com.iidooo.cms.service.channel;

import java.util.List;

import com.iidooo.cms.dto.extend.ChannelDto;
import com.iidooo.cms.dto.extend.SiteDto;
import com.iidooo.passport.dto.extend.RoleDto;

public interface IChannelListService {
    
    List<ChannelDto> getChildrenChannelList(int parentID);
    
    List<ChannelDto> getChildrenChannelList(int siteID, int parentID);

    List<SiteDto> getSiteList(List<RoleDto> roleList);

    SiteDto getTopSite();
    
    /**
     * Delete the channel
     * 
     * @param channel This channel should be delete.
     * @return Delete success or not.
     */
    boolean deleteChannel(ChannelDto channel);
}

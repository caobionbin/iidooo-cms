package com.iidooo.cms.client.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.iidooo.cms.client.service.AttachService;
import com.iidooo.cms.constant.AttributeConstant;
import com.iidooo.cms.dto.extend.CmsAttachDto;
import com.iidooo.cms.dto.extend.CmsChannelDto;
import com.iidooo.cms.dto.extend.CmsContentDto;
import com.iidooo.cms.service.ChannelService;
import com.iidooo.cms.service.ContentService;

public class AttachAction extends CmsBaseAction {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(AttachAction.class);
    
    @Autowired
    private ChannelService channelService;
    
    @Autowired
    private ContentService contentService;
    
    @Autowired
    private AttachService attachService;

    private CmsChannelDto currentChannel;

    private CmsContentDto currentContent;
    
    private CmsAttachDto currentAttach;

    public CmsChannelDto getCurrentChannel() {
        return currentChannel;
    }

    public void setCurrentChannel(CmsChannelDto currentChannel) {
        this.currentChannel = currentChannel;
    }

    public CmsContentDto getCurrentContent() {
        return currentContent;
    }

    public void setCurrentContent(CmsContentDto currentContent) {
        this.currentContent = currentContent;
    }

    public CmsAttachDto getCurrentAttach() {
        return currentAttach;
    }

    public void setCurrentAttach(CmsAttachDto currentAttach) {
        this.currentAttach = currentAttach;
    }

    @Override
    public String execute() throws Exception {
        try {
            HttpServletRequest request = this.getRequest();
            String attachID = request.getParameter(AttributeConstant.ATTACH_ID);
            if (attachID == null || attachID == "") {
                return NONE;
            }
            this.currentAttach = attachService.getAttachDtoByID(Integer.parseInt(attachID));
            if (currentAttach == null) {
                return NONE;
            }
                    
            this.currentContent = contentService.getContentByID(this.currentAttach.getContentID());
            this.currentChannel = channelService.getChannelByID(this.currentContent.getChannelID());
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return ERROR;
        }
    }

}
package com.iidooo.cms.admin.action.channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.iidooo.cms.admin.service.channel.ChannelDetailService;
import com.iidooo.cms.constant.URLConstant;
import com.iidooo.cms.dto.extend.CmsChannelDto;
import com.iidooo.cms.dto.extend.CmsTemplateDto;
import com.iidooo.framework.action.BaseAction;
import com.iidooo.framework.constant.SessionConstant;
import com.iidooo.framework.dto.extend.SecurityUserDto;
import com.iidooo.framework.exception.ExclusiveException;
import com.iidooo.framework.tag.TreeNode;
import com.iidooo.framework.utility.StringUtil;
import com.iidooo.framework.utility.ValidateUtil;

public class ChannelDetailAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(ChannelListAction.class);

    @Autowired
    private ChannelDetailService channelDetailService;

    // The channel tree's root node
    private TreeNode rootTreeNode;

    private CmsChannelDto channel;

    private List<CmsChannelDto> allChannels;

    private List<CmsTemplateDto> allTemplates;

    public TreeNode getRootTreeNode() {
        return rootTreeNode;
    }

    public void setRootTreeNode(TreeNode rootTreeNode) {
        this.rootTreeNode = rootTreeNode;
    }
    
    public CmsChannelDto getChannel() {
        return channel;
    }

    public void setChannel(CmsChannelDto channel) {
        this.channel = channel;
    }

    public List<CmsChannelDto> getAllChannels() {
        return allChannels;
    }

    public void setAllChannels(List<CmsChannelDto> allChannels) {
        this.allChannels = allChannels;
    }

    public List<CmsTemplateDto> getAllTemplates() {
        return allTemplates;
    }

    public void setAllTemplates(List<CmsTemplateDto> allTemplates) {
        this.allTemplates = allTemplates;
    }

    public String init() {
        try {

            // Build the channel tree' root node.
            rootTreeNode = new TreeNode();
            rootTreeNode.setUrl(StringUtil.replace(URLConstant.CHANNEL_LIST_INIT, "0"));
            rootTreeNode.setName(this.getText("LABEL_TREE_ROOT"));

            this.allChannels = channelDetailService.getAllChannels();

            Map<Integer, TreeNode> channelMap = new HashMap<Integer, TreeNode>();
            for (CmsChannelDto channel : allChannels) {
                // Build the tree node of the CmsChannelDto
                TreeNode treeNode = new TreeNode();
                treeNode.setUrl(StringUtil.replace(URLConstant.CHANNEL_DETAIL_INIT, channel.getChannelID().toString()));
                treeNode.setName(channel.getChannelName());
                treeNode.setTag(channel);

                // Set the root tree node as the parent tree node, if the parent ID is 0.
                if (channel.getParentID() == 0) {
                    treeNode.setParent(rootTreeNode);
                }

                channelMap.put(channel.getChannelID(), treeNode);
            }

            // Set the tree node's parent
            for (CmsChannelDto channel : allChannels) {
                if (channel.getParentID() != 0) {
                    TreeNode treeNode = channelMap.get(channel.getChannelID());
                    TreeNode parent = channelMap.get(channel.getParentID());
                    parent.setUrl(StringUtil.replace(URLConstant.CHANNEL_LIST_INIT, channel.getParentID().toString()));
                    treeNode.setParent(parent);
                }
            }

            this.allTemplates = channelDetailService.getAllTemplates();
            
            // The modify mode will trace the channel ID.
            if (channel != null) {
                channel = channelDetailService.getCurrentChannel(channel.getChannelID());
            }
            
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return ERROR;
        }
    }

    public String create() {
        try {
            SecurityUserDto securityUserDto = (SecurityUserDto) this.getSessionValue(SessionConstant.SECURITY_USER);
            channel.setForCreate(securityUserDto.getUserID());
            channelDetailService.createChannel(channel);
            addActionMessage(getText("MSG_CREATE_SUCCESS"));
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return ERROR;
        }
    }

    public void validateCreate() {
        try {
            commonValidate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    public String update() {
        try {
            SecurityUserDto securityUserDto = (SecurityUserDto) this.getSessionValue(SessionConstant.SECURITY_USER);
            channel.setForUpdate(securityUserDto.getUserID());
            channelDetailService.updateChannel(channel);
            addActionMessage(getText("MSG_UPDATE_SUCCESS"));
            return SUCCESS;
        } catch (ExclusiveException exclusive) {
            addActionMessage(getText("MSG_EXCLUSIVE"));
            return INPUT;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return ERROR;
        }
    }

    public void validateUpdate() {
        try {
            commonValidate();
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    public void commonValidate() {
        try {
            if (ValidateUtil.isEmpty(channel.getChannelName())) {
                String msg = this.getText("MSG_COMMON_REQUIRED", new String[] { getText("LABEL_CHANNEL_NAME") });
                addFieldError("channel.channelName", msg);
            }
            if (ValidateUtil.isEmpty(channel.getChannelPath())) {
                String msg = this.getText("MSG_COMMON_REQUIRED", new String[] { getText("LABEL_CHANNEL_PATH") });
                addFieldError("channel.channelPath", msg);
            }
            CmsChannelDto cmsChannelDto = channelDetailService.getChannelByPath(channel.getChannelPath());
            if (cmsChannelDto != null) {
                String msg = this.getText("MSG_CHANNEL_EXISTED", new String[] { getText("LABEL_CHANNEL_PATH") });
                addFieldError("channel.channelPath", msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    public String delete() {
        try {
            channelDetailService.deleteChannel(channel);
            addActionMessage(getText("MSG_DELETE_SUCCESS"));
            return SUCCESS;
        } catch (ExclusiveException exclusive) {
            addActionMessage(getText("MSG_EXCLUSIVE"));
            return INPUT;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return ERROR;
        }
    }
}

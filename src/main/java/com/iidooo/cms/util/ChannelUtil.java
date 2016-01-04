package com.iidooo.cms.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import com.iidooo.cms.constant.CmsConstant;
import com.iidooo.cms.dao.extend.ChannelDao;
import com.iidooo.cms.dto.extend.ChannelDto;
import com.iidooo.core.tag.entity.TreeNode;
import com.iidooo.core.util.MybatisUtil;
import com.iidooo.core.util.StringUtil;

public class ChannelUtil {

    private static final Logger logger = Logger.getLogger(ChannelUtil.class);

    public List<Integer> getOffspringChannelIDList(String siteCode, String channelPath) {
        List<Integer> result = new ArrayList<Integer>();
        SqlSession sqlSession = MybatisUtil.getSqlSessionFactory().openSession();
        try {
            ChannelDao channelDao = sqlSession.getMapper(ChannelDao.class);
            List<ChannelDto> channelList = null;// channelDao.selectBySiteCode(siteCode);
            this.counstructChildren(channelList);
            result = this.getOffspring(channelList, channelPath);
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
        return result;
    }

    public static TreeNode constructChannelTree() {
        SqlSession sqlSession = MybatisUtil.getSqlSessionFactory().openSession();
        TreeNode result = null;
        try {
            ChannelDao channelDao = sqlSession.getMapper(ChannelDao.class);
            List<ChannelDto> allChannels = channelDao.selectAllChannels();

            // 构建栏目列表的父子关系
            counstructChildren(allChannels);

            result = new TreeNode(null);
            result.setUrl(StringUtil.replace(CmsConstant.ACTION_CHANNEL_LIST, "0"));
            result.setName("TREE_ROOT");

            // Key: ChannelID
            // Value: TreeNode
            Map<Integer, TreeNode> createdTreeMap = new HashMap<Integer, TreeNode>();
            for (ChannelDto item : allChannels) {
                TreeNode treeNode = null;
                if (item.getParentID() <= 0) {
                    treeNode = new TreeNode(result);
                } else {
                    treeNode = new TreeNode(null);
                }
                if (item.getChildren().size() > 0) {
                    treeNode.setUrl(StringUtil.replace(CmsConstant.ACTION_CHANNEL_LIST, item.getChannelID().toString()));                    
                }else {
                    treeNode.setUrl(StringUtil.replace(CmsConstant.ACTION_CHANNEL_DETAIL, item.getChannelID().toString()));      
                }
                treeNode.setName(item.getChannelName());
                treeNode.setTag(item);
                createdTreeMap.put(item.getChannelID(), treeNode);
            }

            // 设置树节点的父子关系
            for (Integer channelID : createdTreeMap.keySet()) {
                TreeNode treeNode = createdTreeMap.get(channelID);
                ChannelDto channelDto = (ChannelDto) treeNode.getTag();

                // 设置树节点的父子关系
                if (channelDto.getParentID() > 0) {
                    TreeNode parentTreeNode = createdTreeMap.get(channelDto.getParentID());
                    treeNode.setParent(parentTreeNode);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 构造查询到的channelList各项目之间的父子关系
     * 
     * @param channelList 构造父子关系的对象列表
     */
    public static void counstructChildren(List<ChannelDto> channelList) {
        try {
            Map<Integer, ChannelDto> channelMap = new HashMap<Integer, ChannelDto>();
            for (ChannelDto item : channelList) {
                channelMap.put(item.getChannelID(), item);
            }

            for (ChannelDto item : channelList) {
                int parentID = item.getParentID();

                if (parentID <= 0) {
                    continue;
                }

                ChannelDto parentChannel = channelMap.get(parentID);
                parentChannel.getChildren().add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }

    public List<Integer> getOffspring(List<ChannelDto> channelList, int currentChannelID) {
        List<Integer> result = new ArrayList<Integer>();
        try {
            result.add(currentChannelID);
            for (ChannelDto item : channelList) {
                if (item.getChannelID() == currentChannelID) {
                    appendOffspringChannels(item.getChildren(), result);
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
        return result;
    }

    public List<Integer> getOffspring(List<ChannelDto> channelList, String currentChannelPath) {
        List<Integer> result = new ArrayList<Integer>();
        try {

            for (ChannelDto item : channelList) {
                if (item.getChannelPath().equals(currentChannelPath)) {

                    // First add the channel's self in the offspring return result list.
                    result.add(item.getChannelID());

                    appendOffspringChannels(item.getChildren(), result);
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
        return result;
    }

    public void appendOffspringChannels(List<ChannelDto> children, List<Integer> offspringChannels) {
        try {
            for (ChannelDto child : children) {
                offspringChannels.add(child.getChannelID());
                appendOffspringChannels(child.getChildren(), offspringChannels);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
        }
    }
}

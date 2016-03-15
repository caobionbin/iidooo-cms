package com.iidooo.cms.dao.extend;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iidooo.cms.dto.extend.CmsContentDto;
import com.iidooo.core.model.Page;

public interface CmsContentDao {
    List<CmsContentDto> selectContentListByType(@Param("channelPath")String channelPath, @Param("contentType")String contentType, @Param("page")Page page);
}
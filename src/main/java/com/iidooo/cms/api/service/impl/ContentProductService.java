package com.iidooo.cms.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iidooo.cms.api.service.IContentProductService;
import com.iidooo.cms.dao.extend.ContentProductDao;
import com.iidooo.cms.dto.extend.ContentProductDto;

@Service
public class ContentProductService implements IContentProductService {

    private static final Logger logger = Logger.getLogger(ContentProductService.class);

    @Autowired
    private ContentProductDao contentProductDao;
    
    @Override
    public ContentProductDto getContentProduct(int contentID) {
        try {
            ContentProductDto result = null;
            
            result = contentProductDao.selectByContentID(contentID);
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.fatal(e);
            return null;
        }
    }

}
package com.pusilkom.ess.service;

import com.pusilkom.ess.model.App;
import com.pusilkom.ess.model.AppExample;
import com.pusilkom.ess.model.mapper.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by fahri on 3/8/17.
 */

@Service
@Transactional(readOnly = true)
public class AppService {

    @Autowired AppMapper appMapper;

    public App getAppByKode(String kode) {
        AppExample example = new AppExample();
        example.createCriteria()
                .andKodeEqualTo(kode);

        List<App> list = appMapper.selectByExample(example);

        return list.isEmpty() ? null : list.get(0);
    }
}

package com.vivareal.spotippos.repository.memory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.vivareal.spotippos.model.Province;
import com.vivareal.spotippos.repository.ProvinceRepository;

@Repository
public class MemoryProvinceRepository implements ProvinceRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryProvinceRepository.class);

    private Map<String, Province> provinceDb;

    public MemoryProvinceRepository() {
        provinceDb = new ConcurrentHashMap<>();
    }

    @Override
    public Province add(Province province) {
        LOGGER.info("Method[add] province[{}]", province);
        provinceDb.put(province.getName(), province);
        return province;
    }

}
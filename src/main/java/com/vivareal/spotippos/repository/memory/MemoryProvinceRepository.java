package com.vivareal.spotippos.repository.memory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.vivareal.spotippos.model.Province;
import com.vivareal.spotippos.repository.ProvinceRepository;

@Repository
public class MemoryProvinceRepository implements ProvinceRepository {

	private Map<String, Province> provinceDb;

	public MemoryProvinceRepository() {
		provinceDb = new ConcurrentHashMap<>();
	}

	@Override
	public Province add(Province province) {
		provinceDb.put(province.getName(), province);
		return province;
	}

}
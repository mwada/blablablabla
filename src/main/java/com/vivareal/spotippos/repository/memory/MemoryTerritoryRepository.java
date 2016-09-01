package com.vivareal.spotippos.repository.memory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.vivareal.spotippos.model.Boundaries;
import com.vivareal.spotippos.model.Coordinate;
import com.vivareal.spotippos.model.Position;
import com.vivareal.spotippos.repository.TerritoryRepository;

@Repository
public class MemoryTerritoryRepository implements TerritoryRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryTerritoryRepository.class);

    private Map<Coordinate, Position> territoryDb;

    public MemoryTerritoryRepository() {
        territoryDb = new ConcurrentHashMap<>();
    }

    @Override
    public Set<String> findProvinces(Coordinate coordinate) {
        LOGGER.info("Method[findProvinces] coordinate[{}]", coordinate);
        Position position = territoryDb.getOrDefault(coordinate, new Position());
        return position.getProvinceNames();
    }

    @Override
    public void addProperty(Coordinate coordinate, Long id) {
        LOGGER.info("Method[addProperty] coordinate[{}] id[{}]", coordinate, id);
        Position position = territoryDb.getOrDefault(coordinate, new Position(coordinate));
        position.addProperty(id);
        territoryDb.put(coordinate, position);
    }

    @Override
    public Set<Long> findProperties(Boundaries boundaries) {
        LOGGER.info("Method[findProperties] boundaries[{}]", boundaries);
        Set<Long> ids = new HashSet<>();
        for (Coordinate coordinate : boundaries.getCoordinates()) {
            Position position = territoryDb.getOrDefault(coordinate, new Position());
            ids.addAll(position.getPropertyIds());
        }
        return ids;
    }

    @Override
    public void addProvince(Boundaries boundaries, String name) {
        LOGGER.info("Method[addProvince] boundaries[{}] name[{}]", boundaries, name);
        boundaries.getCoordinates().forEach(c -> addProvince(c, name));
    }

    protected void addProvince(Coordinate coordinate, String name) {
        Position position = territoryDb.getOrDefault(coordinate, new Position(coordinate));
        position.addProvince(name);
        territoryDb.put(coordinate, position);
    }

}
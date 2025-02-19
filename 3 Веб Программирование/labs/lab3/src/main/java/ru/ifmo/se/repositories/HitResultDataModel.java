package ru.ifmo.se.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import ru.ifmo.se.models.Attempt;

import java.util.List;
import java.util.Map;

@Named("attemptsList")
@ApplicationScoped
public class HitResultDataModel extends LazyDataModel<Attempt> {

    @Inject
    private HitResultRepository service;

    @Override
    public int count(Map<String, FilterMeta> map) {
        return service.getAttemptsCount();
    }

    @Override
    public List<Attempt> load(int first, int pageSize, Map<String, SortMeta> map, Map<String, FilterMeta> map1) {
        return service.getAttemptsList(first, pageSize);
    }

}

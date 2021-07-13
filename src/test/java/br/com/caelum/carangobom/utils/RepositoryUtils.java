package br.com.caelum.carangobom.utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RepositoryUtils<T> {

    public <T extends EntityId> Long generateId(List<T> list){
        List<Long> ids = list
                .stream()
                .map(T::getId)
                .collect(Collectors.toList());
        Long maxId = Collections.max(ids);
        if(maxId==null) {
            maxId = 0L;
        }
        return maxId+1;
    }
}

package inf.unideb.webbackend.service;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

@Service
public class BaseService<D, E> {

    private Type dtoClass;

    private Type entityClass;

    private ModelMapper modelMapper;

    public BaseService() {
    }

    public BaseService(final Class<D> dtoClass, final Class<E> entityClass) {
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    @Autowired
    public void setModelMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public ModelMapper getModelMapper() {
        return this.modelMapper;
    }

    public D mapToDTO(final E entity) {
        return getModelMapper().map(entity, dtoClass);
    }

    public E mapFromDTO(final D dto) {
        return getModelMapper().map(dto, entityClass);
    }
}

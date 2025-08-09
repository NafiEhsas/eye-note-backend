package com.example.eyenote.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.example.eyenote.ApplicationContextProvider;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ObjectMapperUtils {
    private static final ModelMapper modelMapper;

    private static ModelMapper getModelMapper() {
        return ApplicationContextProvider.getApplicationContext().getBean(ModelMapper.class);
    }

    /**
     * Model mapper property setting are specified in the following block.
     * Default property matching strategy is set to Strict see {@link MatchingStrategies}
     * Custom mappings are added using {@link ModelMapper#addMappings(PropertyMap)}
     */
    static {
        modelMapper = getModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
    }


    /**
     * Hide from public usage
     */
    private ObjectMapperUtils() {}

    /**
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <D>       type of result object.
     * @param <T>       type of source object to map from.
     * @param entity    entity that needs to be mapped
     * @param outClass  class of result object.
     * @return new object of <code>outClass</code> type
     */
    public static <D, T> D map(final T entity, Class<D> outClass) {
        return modelMapper.map(entity, outClass);
    }

    public static <D, T> D map(final T entity, Class<D> outClass, MatchingStrategy matchingStrategy) {
        modelMapper.getConfiguration().setMatchingStrategy(matchingStrategy);
        D d = modelMapper.map(entity, outClass);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return d;
    }

    /**
     * <p>Note: outClass object must have default constructor with no argument</p>
     *
     * @param entityList list of entities that needs to be mapped
     * @param outClass   class of result list element 
     * @param <D>        type of objects in result list
     * @param <T>        type of entity in <code>entityList</code>
     * @return list of mapped object with <code><D></code> type
     */
    public static <D, T> List<D> map(final Collection<T> entityList, Class<D> outClass) {
        return entityList.stream()
                .map(entity -> map(entity, outClass))
                .collect(Collectors.toList());
    }

    public static <D, T> List<D> map(final Collection<T> entityList, Class<D> outClass, MatchingStrategy matchingStrategy) {
        modelMapper.getConfiguration().setMatchingStrategy(matchingStrategy);
        List<D> ds = entityList.stream()
                .map(entity -> map(entity, outClass))
                .collect(Collectors.toList());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return ds;
    }


    /**
     * <p>Note: outClass object must have default constructor with no argument</p>
     *
     * @param entityPage list of entities that needs to be mapped
     * @param outClass   class of result page element
     * @param <D>        type of objects in result page
     * @param <T>        type of entity in <code>entityPage</code>
     * @return page of mapped object with <code><D></code> type
     */
    public static <D, T> Page<D> map(final Page<T> entityPage, Class<D> outClass) {
        List<D> dtoList = entityPage.getContent().stream()
                .map(entity -> map(entity, outClass))
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, PageRequest.of(entityPage.getNumber(), entityPage.getSize()), entityPage.getTotalElements());
    }

    public static <D, T> Page<D> map(final Page<T> entityPage, Class<D> outClass, MatchingStrategy matchingStrategy) {
        modelMapper.getConfiguration().setMatchingStrategy(matchingStrategy);
        List<D> dtoList = entityPage.getContent().stream()
                .map(entity -> map(entity, outClass))
                .collect(Collectors.toList());

        Page<D> pd = new PageImpl<>(dtoList, PageRequest.of(entityPage.getNumber(), entityPage.getSize()), entityPage.getTotalElements());
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return pd;
    }

    /**
     * Maps {@code source} to {@code destination}.
     *
     * @param source        object to map from
     * @param destination   object to map to
     */
    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }

    public static <S, D> D map(final S source, D destination, MatchingStrategy matchingStrategy) {
        modelMapper.getConfiguration().setMatchingStrategy(matchingStrategy);
        modelMapper.map(source, destination);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return destination;
    }

}

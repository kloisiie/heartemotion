package com.brframework.commonwebbase.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.brframework.commonwebbase.dao.DictionaryDao;
import com.brframework.commonwebbase.entity.Dictionary;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xu
 * <p>
 * 字典
 *
 * @author xu
 * @date 18-4-19 上午10:07
 */
@Service
@CacheConfig(cacheNames = "default")
public class DictionaryService {

    @Autowired
    DictionaryDao dictionaryDao;

    /**
     * 保存key value
     *
     * @param key
     * @param value
     */
    @CacheEvict(key = "targetClass + '-key-' + #key")
    public void set(String key, String value) {

        Dictionary dictionary = dictionaryDao.findByKey(key);
        if (dictionary == null) {
            dictionary = new Dictionary();
            dictionary.setKey(key);
            dictionary.setCreateDate(LocalDateTime.now());
        }

        dictionary.setValue(value);
        dictionary.setUpdateDate(LocalDateTime.now());

        dictionaryDao.save(dictionary);
    }


    /**
     * 获取 value 对象
     *
     * @param key
     * @return
     */
    @Cacheable(key = "targetClass + '-key-' + #key")
    public String get(String key) {

        Dictionary dictionary = dictionaryDao.findByKey(key);
        if (dictionary == null) {
            return "";
        }

        return dictionary.getValue();
    }

    public <T> ListHandle<T> buildListHandle(String dictionaryKey,
                                                 TypeReference<List<T>> type){

        return new ListHandle<>(dictionaryKey, this, type);
    }

    /**
     * 字典的List操作形式
     * @param <T>
     */
    public static class ListHandle<T>{
        private String dictionaryKey;
        private DictionaryService dictionaryService;
        private TypeReference<List<T>> type;

        private ListHandle(String dictionaryKey, DictionaryService dictionaryService,
                           TypeReference<List<T>> type){
            this.dictionaryKey = dictionaryKey;
            this.dictionaryService = dictionaryService;
            this.type = type;
        }

        public List<T> getList(){
            String valueString = dictionaryService.get(dictionaryKey);
            if (Strings.isNullOrEmpty(valueString)) {
                return Lists.newArrayList();
            }
            return JSON.parseObject(valueString, type);
        }

        public void set(List<T> list){
            dictionaryService.set(dictionaryKey, JSON.toJSONString(list));
        }

        public void add(T value){
            List<T> ts = getList();
            ts.add(value);
            set(ts);
        }

        public T get(int index){
            List<T> ts = getList();
            return ts.get(index);
        }

    }

    public <K, V> MapHandle<K, V> buildMapHandle(String dictionaryKey,
                                                 TypeReference<Map<K, V>> type){

        return new MapHandle<>(dictionaryKey, this, type);
    }

    /**
     * 字典的Map操作形式
     * @param <K>
     * @param <V>
     */
    public static class MapHandle<K, V> {

        private String dictionaryKey;
        private DictionaryService dictionaryService;
        private TypeReference<Map<K, V>> type;

        public MapHandle(String dictionaryKey, DictionaryService dictionaryService, TypeReference<Map<K, V>> type){
            this.dictionaryKey = dictionaryKey;
            this.dictionaryService = dictionaryService;
            this.type = type;
        }

        public void setMap(Map<K, V> map){
            dictionaryService.set(dictionaryKey, JSON.toJSONString(map));
        }

        public Map<K, V> getMap(){
            String valueString = dictionaryService.get(dictionaryKey);
            if (Strings.isNullOrEmpty(valueString)) {
                return Maps.newHashMap();
            }

            return JSON.parseObject(valueString, type);
        }

        public V get(K key){

            String valueString = dictionaryService.get(dictionaryKey);
            if (Strings.isNullOrEmpty(valueString)) {
                return null;
            }
            Map<K, V> map = JSON.parseObject(valueString, type);
            return map.get(key);
        }

        public boolean containsKey(K key){
            String valueString = dictionaryService.get(dictionaryKey);
            if (Strings.isNullOrEmpty(valueString)) {
                return false;
            }
            Map<K, V> map = JSON.parseObject(valueString, type);
            return map.containsKey(key);
        }

        public void put(K key, V value){
            String valueString = dictionaryService.get(dictionaryKey);
            Map<K, V> map;
            if (Strings.isNullOrEmpty(valueString)) {
                map = new HashMap<>();
            } else {
                map = JSON.parseObject(valueString, type);
            }
            map.put(key, value);
            setMap(map);
        }

        public void remove(K key){
            String valueString = dictionaryService.get(dictionaryKey);
            if (Strings.isNullOrEmpty(valueString)) {
                return;
            }
            Map<K, V> map = JSON.parseObject(valueString, type);
            map.remove(key);
            setMap(map);
        }

        public List<V> getValuesToList(){
            String valueString = dictionaryService.get(dictionaryKey);
            if (Strings.isNullOrEmpty(valueString)) {
                return Lists.newArrayList();
            }
            Map<K, V> map = JSON.parseObject(valueString, type);
            return Lists.newArrayList(map.values());
        }

    }

}

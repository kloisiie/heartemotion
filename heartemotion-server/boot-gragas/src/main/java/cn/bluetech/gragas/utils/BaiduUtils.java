package cn.bluetech.gragas.utils;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.brframework.commonweb.exception.HandleException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 百度api调用工具
 * @author xu
 * @date 2020/4/29 10:26
 */
public class BaiduUtils {

    /** AK */
    public static final String AK = "HdUv1Od5QekCRz5qHMmgi7hf0XK4EsTB";
    /** SK */
    public static final String SK = "f441qhwNGSKVTIcm0bQ9Q4ELHmhTVeg5";

    /**
     * 反地理编码
     * @return  地理编码映射的位置
     */
    public static Location geocoding(String address){
        String uri = "/geocoding/v3/";
        TreeMap<String, Object> paramsMap = new TreeMap<>();
        paramsMap.put("address", address);
        String sn = getSN(uri, paramsMap);
        paramsMap.put("sn", sn);


        JSONObject response = request(uri, paramsMap);
        Location location = new Location();
        location.setLatitude(response.getJSONObject("result").getJSONObject("location").getDouble("lat"));
        location.setLongitude(response.getJSONObject("result").getJSONObject("location").getDouble("lng"));

        return location;
    }

    /**
     * ip转地理位置
     * @param ip ip
     * @return ip的大概地理位置
     */
    public static IpLocation geoip(String ip){
        String uri = "/location/ip";
        TreeMap<String, Object> paramsMap = new TreeMap<>();
        paramsMap.put("ip", ip);
        String sn = getSN(uri, paramsMap);
        paramsMap.put("sn", sn);


        JSONObject response = request(uri, paramsMap);

        IpLocation location = new IpLocation();
        location.setProvince(response.getJSONObject("content").getJSONObject("address_detail").getString("province"));
        location.setCity(response.getJSONObject("content").getJSONObject("address_detail").getString("city"));
        location.setAddress(response.getJSONObject("content").getString("address"));

        return location;
    }

    public static void main(String[] args) {
        System.out.println(geocoding("广东省广州市番禺区时代E-PARK"));
//        System.out.println(geocoding(geoip("59.41.22.248").getAddress()));
    }


    private static JSONObject request(String uri, TreeMap<String, Object> paramsMap){
        JSONObject json = JSON.parseObject(HttpUtil.get("http://api.map.baidu.com" + uri, paramsMap));
        if(json.getIntValue("status") != 0){
            throw new HandleException("百度地图API调用错误");
        }
        return json;
    }


    @Data
    public static class Location{
        /** 经度 */
        private Double longitude;
        /** 纬度 */
        private Double latitude;
    }

    @Data
    public static class IpLocation{
        /** 省 */
        private String province;
        /** 市 */
        private String city;
        /** 地址(广东省广州市) */
        private String address;
    }


    private static String getSN(String uri, TreeMap<String, Object> paramMap){
        paramMap.put("output", "json");
        paramMap.put("ak", AK);
        String paramsStr = StringUtils.join(paramMap.keySet().stream()
                .map(key -> {
                    try {
                        return key + "=" + URLEncoder.encode(paramMap.get(key).toString(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList()), "&") + SK;
        String wholeStr = uri + "?" + paramsStr;


        String tempStr;
        try {
            tempStr = URLEncoder.encode(wholeStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        return MD5.create().digestHex(tempStr);
    }

}

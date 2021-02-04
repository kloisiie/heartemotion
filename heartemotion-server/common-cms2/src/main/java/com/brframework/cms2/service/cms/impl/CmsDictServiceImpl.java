package com.brframework.cms2.service.cms.impl;

import com.brframework.cms2.core.CmsDict;
import com.brframework.cms2.json.admin.cms.CmsDictEntry;
import com.brframework.cms2.service.cms.CmsDictService;
import com.brframework.commonweb.core.SpringContext;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xu
 * @date 2020/11/17 14:59
 */
@Service
public class CmsDictServiceImpl implements CmsDictService {
    @Override
    public CmsDict getCmsDict(String key) {

        List<CmsDict> beansOfType = SpringContext.getBeansOfType(CmsDict.class);
        for (CmsDict cmsDict : beansOfType) {
            if(cmsDict.dictKey().equals(key)){
                return cmsDict;
            }
        }
        return null;
    }


    @Component
    static class CmsDict1 implements CmsDict{

        @Override
        public String dictKey() {
            return "test-area";
        }

        @Override
        public List<CmsDictEntry> dictEntry(String... childKeys) {
            List<CmsDictEntry> entries = Lists.newArrayList();

            entries.add(new CmsDictEntry("01", "广东省", Lists.newArrayList(
                    new CmsDictEntry("0101", "广州市", Lists.newArrayList(
                            new CmsDictEntry("010101", "番禺区"),
                            new CmsDictEntry("010102", "天河区")
                    )),
                    new CmsDictEntry("0102", "深圳特区", Lists.newArrayList(
                            new CmsDictEntry("010201", "光明区"),
                            new CmsDictEntry("010202", "南山区")
                    ))
            )));
            entries.add(new CmsDictEntry("02", "广西壮族自治区", Lists.newArrayList(
                    new CmsDictEntry("0201", "南宁市", Lists.newArrayList(
                            new CmsDictEntry("020101", "武鸣区"),
                            new CmsDictEntry("020102", "青秀区")
                    )),
                    new CmsDictEntry("0202", "桂林市", Lists.newArrayList(
                            new CmsDictEntry("020201", "七星区"),
                            new CmsDictEntry("020202", "秀峰区")
                    ))
            )));

            return entries;
        }

    }


    @Component
    static class CmsDict2 implements CmsDict{

        @Override
        public String dictKey() {
            return "test-area-query";
        }

        @Override
        public List<CmsDictEntry> dictEntry(String... childKeys) {
            List<CmsDictEntry> entries = Lists.newArrayList();

            entries.add(new CmsDictEntry("00", "不限", Lists.newArrayList(
                    new CmsDictEntry("0000", "不限", Lists.newArrayList(
                            new CmsDictEntry("000000", "不限")
                    ))
            )));

            entries.add(new CmsDictEntry("01", "广东省", Lists.newArrayList(
                    new CmsDictEntry("0101", "广州市", Lists.newArrayList(
                            new CmsDictEntry("010101", "番禺区"),
                            new CmsDictEntry("010102", "天河区")
                    )),
                    new CmsDictEntry("0102", "深圳特区", Lists.newArrayList(
                            new CmsDictEntry("010201", "光明区"),
                            new CmsDictEntry("010202", "南山区")
                    ))
            )));
            entries.add(new CmsDictEntry("02", "广西壮族自治区", Lists.newArrayList(
                    new CmsDictEntry("0201", "南宁市", Lists.newArrayList(
                            new CmsDictEntry("020101", "武鸣区"),
                            new CmsDictEntry("020102", "青秀区")
                    )),
                    new CmsDictEntry("0202", "桂林市", Lists.newArrayList(
                            new CmsDictEntry("020201", "七星区"),
                            new CmsDictEntry("020202", "秀峰区")
                    ))
            )));

            return entries;
        }

    }
}

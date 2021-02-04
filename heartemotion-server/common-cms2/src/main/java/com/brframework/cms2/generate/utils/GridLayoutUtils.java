package com.brframework.cms2.generate.utils;

import com.brframework.cms2.generate.column.ColumnEntry;
import com.brframework.cms2.generate.layout.ColLayoutEntry;
import com.brframework.cms2.generate.layout.GridLayoutEntry;

import java.util.List;

/**
 * @author xu
 * @date 2020/12/9 14:51
 */
public class GridLayoutUtils {

    /**
     * 自动Grid布局
     * @param colNumber   列数量
     * @param columns     列对象
     * @return  Grid布局
     */
    public static GridLayoutEntry newGridLayout(int colNumber, List<ColumnEntry> columns){
        GridLayoutEntry gridLayout = GridLayoutEntry.builder().build();
        for (int i = 0; i < colNumber; i++) {
            gridLayout.getColumns().add(ColLayoutEntry.builder().build());
        }

        for (int i = 0; i < columns.size(); i++) {
            gridLayout.getColumns().get(i % colNumber).getColumns().add(columns.get(i));
        }
        return gridLayout;
    }

}

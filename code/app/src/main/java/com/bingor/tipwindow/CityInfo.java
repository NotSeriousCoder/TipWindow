package net.bingosoft.generallib.db.table;

import com.bingor.poptipwindow.view.wheel.WheelItem;

import java.util.List;

/**
 * Created by HXB on 2018/6/27.
 */
public class CityInfo implements WheelItem {
    private String id;
    private String value;
    private List<CityInfo> childs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<CityInfo> getChilds() {
        return childs;
    }

    public void setChilds(List<CityInfo> childs) {
        this.childs = childs;
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "value='" + value + '\'' +
                ", childs=" + childs +
                '}';
    }

    @Override
    public String getName() {
        return value;
    }

    @Override
    public List<? extends WheelItem> getChildren() {
        return childs;
    }
}

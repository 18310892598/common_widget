package ola.com.pickerview.utils;


import ola.com.pickerview.interfaces.IPickerViewData;

public class StationList extends Entity implements IPickerViewData {
    public String latitude;
    public String stationName;
    public String longitude;

    @Override
    public String getPickerViewText() {
        return stationName;
    }
}

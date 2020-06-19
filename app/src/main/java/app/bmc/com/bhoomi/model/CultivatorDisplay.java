package app.bmc.com.bhoomi.model;

/**
 * Author Name:Venkat Purimitla
 * Date       :2019-01-31
 * Description : This is model class  defined for Cultivator to display in list.
 */

public class CultivatorDisplay {
    private String year_season;
    private String cultivator_name;
    private String cult_type;
    private String tenancy_extent;
    private String tenancy_rent;
    private String land_utilisation_cls;
    private String land_utilisation_rent;
    private String dry_wet_garden;
    private String crop_name;
    private String single_crop_extents;
    private String mixed_crop_extents;
    private String total_crop_extents;
    private String water_source;
    private String yield;
    private String mixed_crop_name;
    private String mixed_crop_name_extents;

    public CultivatorDisplay(String year_season, String cultivator_name, String cult_type, String tenancy_extent, String tenancy_rent, String land_utilisation_cls, String land_utilisation_rent, String dry_wet_garden, String crop_name, String single_crop_extents, String mixed_crop_extents, String total_crop_extents, String water_source, String yield, String mixed_crop_name, String mixed_crop_name_extents) {
        this.year_season = year_season;
        this.cultivator_name = cultivator_name;
        this.cult_type = cult_type;
        this.tenancy_extent = tenancy_extent;
        this.tenancy_rent = tenancy_rent;
        this.land_utilisation_cls = land_utilisation_cls;
        this.land_utilisation_rent = land_utilisation_rent;
        this.dry_wet_garden = dry_wet_garden;
        this.crop_name = crop_name;
        this.single_crop_extents = single_crop_extents;
        this.mixed_crop_extents = mixed_crop_extents;
        this.total_crop_extents = total_crop_extents;
        this.water_source = water_source;
        this.yield = yield;
        this.mixed_crop_name = mixed_crop_name;
        this.mixed_crop_name_extents = mixed_crop_name_extents;
    }

    public CultivatorDisplay() {

    }

    public String getYear_season() {
        return year_season == null ? "" : year_season;
    }

    public void setYear_season(String year_season) {
        this.year_season = year_season;
    }

    public String getCultivator_name() {
        return cultivator_name == null ? "" : cultivator_name;
    }

    public void setCultivator_name(String cultivator_name) {
        this.cultivator_name = cultivator_name;
    }

    public String getCult_type() {
        return cult_type == null ? "" : cult_type;
    }

    public void setCult_type(String cult_type) {
        this.cult_type = cult_type;
    }

    public String getTenancy_extent() {
        return tenancy_extent == null ? "" : tenancy_extent;
    }

    public void setTenancy_extent(String tenancy_extent) {
        this.tenancy_extent = tenancy_extent;
    }

    public String getTenancy_rent() {
        return tenancy_rent == null ? "" : tenancy_rent;
    }

    public void setTenancy_rent(String tenancy_rent) {
        this.tenancy_rent = tenancy_rent;
    }

    public String getLand_utilisation_cls() {
        return land_utilisation_cls == null ? "" : land_utilisation_cls;
    }

    public void setLand_utilisation_cls(String land_utilisation_cls) {
        this.land_utilisation_cls = land_utilisation_cls;
    }

    public String getLand_utilisation_rent() {
        return land_utilisation_rent == null ? "" : land_utilisation_rent;
    }

    public void setLand_utilisation_rent(String land_utilisation_rent) {
        this.land_utilisation_rent = land_utilisation_rent;
    }

    public String getDry_wet_garden() {
        return dry_wet_garden == null ? "" : dry_wet_garden;
    }

    public void setDry_wet_garden(String dry_wet_garden) {
        this.dry_wet_garden = dry_wet_garden;
    }

    public String getCrop_name() {
        return crop_name == null ? "" : crop_name;
    }

    public void setCrop_name(String crop_name) {
        this.crop_name = crop_name;
    }

    public String getSingle_crop_extents() {
        return single_crop_extents == null ? "" : single_crop_extents;
    }

    public void setSingle_crop_extents(String single_crop_extents) {
        this.single_crop_extents = single_crop_extents;
    }

    public String getMixed_crop_extents() {
        return mixed_crop_extents == null ? "" : mixed_crop_extents;
    }

    public void setMixed_crop_extents(String mixed_crop_extents) {
        this.mixed_crop_extents = mixed_crop_extents;
    }

    public String getTotal_crop_extents() {
        return total_crop_extents == null ? "" : total_crop_extents;
    }

    public void setTotal_crop_extents(String total_crop_extents) {
        this.total_crop_extents = total_crop_extents;
    }

    public String getWater_source() {
        return water_source == null ? "" : water_source;
    }

    public void setWater_source(String water_source) {
        this.water_source = water_source;
    }

    public String getYield() {
        return yield == null ? "" : yield;
    }

    public void setYield(String yield) {
        this.yield = yield;
    }

    public String getMixed_crop_name() {
        return mixed_crop_name == null ? "" : mixed_crop_name;
    }

    public void setMixed_crop_name(String mixed_crop_name) {
        this.mixed_crop_name = mixed_crop_name;
    }

    public String getMixed_crop_name_extents() {
        return mixed_crop_name_extents == null ? "" : mixed_crop_name_extents;
    }

    public void setMixed_crop_name_extents(String mixed_crop_name_extents) {
        this.mixed_crop_name_extents = mixed_crop_name_extents;
    }

}

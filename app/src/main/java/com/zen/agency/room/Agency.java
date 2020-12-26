package com.zen.agency.room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.zen.agency.util.DateConverter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity(tableName ="agency_table")
public class Agency implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id ;


    /**
     * the type (house, flat, office, etc.) ;
     * ● the price in €;
     * ● the surface area in m²;
     * ● the number of rooms;
     * ● the description;
     * ● the address;
     * ● the latitude;
     * ● the longitude;
     * ● the date of the creation of the advert;
     * ● the date of last update of the advert;
     * ● the status of the property (not sold or sold);
     * ● the name of the real estate agent who created the advert.
     * @param title
     * @param imageUrl
     */
    private String title ;
    private String userId ;
    private String price ;
    private String type ;
    private boolean sold ;
    private String surface ;
    private int roomNum ;
    private String description ;
    private String address ;
    private Long latitude ;
    private Long longitude ;
    private Date createTime ;
    private Date updateTime ;
    private String createName ;

    @Ignore
    public Agency() {
    }

    public Agency(int id, String title,String type,boolean sold ,String price, String surface,String userId, int roomNum, String description, String address, Long latitude, Long longitude, Date createTime, Date updateTime, String createName) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.sold = sold;
        this.type = type;
        this.userId = userId;
        this.surface = surface;
        this.roomNum = roomNum;
        this.description = description;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.createName = createName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = new Date();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Agency{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", userId='" + userId + '\'' +
                ", price='" + price + '\'' +
                ", type='" + type + '\'' +
                ", sold=" + sold +
                ", surface='" + surface + '\'' +
                ", roomNum=" + roomNum +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createName='" + createName + '\'' +
                '}';
    }
}

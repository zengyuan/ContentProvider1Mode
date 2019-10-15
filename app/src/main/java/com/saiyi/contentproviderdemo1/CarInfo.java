package com.saiyi.contentproviderdemo1;
/**
 * <pre>
 *     author : Finn
 *     e-mail : 892603597@qq.com
 *     time   : 2019/10/14
 *     desc   : https://www.cnblogs.com/finn21/
 * </pre>
 */
public class CarInfo   {

    private String az = "";
    private String carName= "";
    private String carType= "";
    private String carDate= "";
    private String carLengM = "";
    private int Sees = 0;

    public CarInfo(String az, String carName, String carType, String carDate, String carLengM) {
        this.az = az;
        this.carName = carName;
        this.carType = carType;
        this.carDate = carDate;
        this.carLengM = carLengM;
    }

    public int getSees() {
        return Sees;
    }

    public void setSees(int sees) {
        Sees = sees;
    }

    public String getAz() {
        return az;
    }

    public void setAz(String az) {
        this.az = az;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarDate() {
        return carDate;
    }

    public void setCarDate(String carDate) {
        this.carDate = carDate;
    }

    public String getCarLengM() {
        return carLengM;
    }

    public void setCarLengM(String carLengM) {
        this.carLengM = carLengM;
    }
}

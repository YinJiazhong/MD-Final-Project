package com.zen.agency.entity;

public class Currency {


    /**
     * rates : {"CAD":1.5508,"HKD":9.3895,"ISK":152.9,"PHP":58.359,"DKK":7.4436,"HUF":360.1,"CZK":26.398,"AUD":1.6334,"RON":4.8728,"SEK":10.2358,"IDR":17134.16,"INR":89.203,"BRL":6.1906,"RUB":88.764,"HRK":7.5403,"JPY":126.18,"THB":36.415,"CHF":1.0781,"SGD":1.6209,"PLN":4.4663,"BGN":1.9558,"TRY":9.487,"CNY":7.9115,"NOK":10.6283,"NZD":1.722,"ZAR":18.2159,"USD":1.2114,"MXN":24.0683,"ILS":3.9414,"GBP":0.91143,"KRW":1314.53,"MYR":4.9328}
     * base : EUR
     * date : 2020-12-08
     */

    private RatesBean rates;
    private String base;
    private String date;

    public RatesBean getRates() {
        return rates;
    }

    public void setRates(RatesBean rates) {
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static class RatesBean {
        /**
         * CAD : 1.5508
         * HKD : 9.3895
         * ISK : 152.9
         * PHP : 58.359
         * DKK : 7.4436
         * HUF : 360.1
         * CZK : 26.398
         * AUD : 1.6334
         * RON : 4.8728
         * SEK : 10.2358
         * IDR : 17134.16
         * INR : 89.203
         * BRL : 6.1906
         * RUB : 88.764
         * HRK : 7.5403
         * JPY : 126.18
         * THB : 36.415
         * CHF : 1.0781
         * SGD : 1.6209
         * PLN : 4.4663
         * BGN : 1.9558
         * TRY : 9.487
         * CNY : 7.9115
         * NOK : 10.6283
         * NZD : 1.722
         * ZAR : 18.2159
         * USD : 1.2114
         * MXN : 24.0683
         * ILS : 3.9414
         * GBP : 0.91143
         * KRW : 1314.53
         * MYR : 4.9328
         */

        private Double CAD;
        private Double HKD;
        private Double ISK;
        private Double PHP;
        private Double DKK;
        private Double HUF;
        private Double CZK;
        private Double AUD;
        private Double RON;
        private Double SEK;
        private Double IDR;
        private Double INR;
        private Double BRL;
        private Double RUB;
        private Double HRK;
        private Double JPY;
        private Double THB;
        private Double CHF;
        private Double SGD;
        private Double PLN;
        private Double BGN;
        private Double TRY;
        private Double CNY;
        private Double NOK;
        private Double NZD;
        private Double ZAR;
        private Double USD;
        private Double MXN;
        private Double ILS;
        private Double GBP;
        private Double KRW;
        private Double MYR;

        public Double getCAD() {
            return CAD;
        }

        public void setCAD(Double CAD) {
            this.CAD = CAD;
        }

        public Double getHKD() {
            return HKD;
        }

        public void setHKD(Double HKD) {
            this.HKD = HKD;
        }

        public Double getISK() {
            return ISK;
        }

        public void setISK(Double ISK) {
            this.ISK = ISK;
        }

        public Double getPHP() {
            return PHP;
        }

        public void setPHP(Double PHP) {
            this.PHP = PHP;
        }

        public Double getDKK() {
            return DKK;
        }

        public void setDKK(Double DKK) {
            this.DKK = DKK;
        }

        public Double getHUF() {
            return HUF;
        }

        public void setHUF(Double HUF) {
            this.HUF = HUF;
        }

        public Double getCZK() {
            return CZK;
        }

        public void setCZK(Double CZK) {
            this.CZK = CZK;
        }

        public Double getAUD() {
            return AUD;
        }

        public void setAUD(Double AUD) {
            this.AUD = AUD;
        }

        public Double getRON() {
            return RON;
        }

        public void setRON(Double RON) {
            this.RON = RON;
        }

        public Double getSEK() {
            return SEK;
        }

        public void setSEK(Double SEK) {
            this.SEK = SEK;
        }

        public Double getIDR() {
            return IDR;
        }

        public void setIDR(Double IDR) {
            this.IDR = IDR;
        }

        public Double getINR() {
            return INR;
        }

        public void setINR(Double INR) {
            this.INR = INR;
        }

        public Double getBRL() {
            return BRL;
        }

        public void setBRL(Double BRL) {
            this.BRL = BRL;
        }

        public Double getRUB() {
            return RUB;
        }

        public void setRUB(Double RUB) {
            this.RUB = RUB;
        }

        public Double getHRK() {
            return HRK;
        }

        public void setHRK(Double HRK) {
            this.HRK = HRK;
        }

        public Double getJPY() {
            return JPY;
        }

        public void setJPY(Double JPY) {
            this.JPY = JPY;
        }

        public Double getTHB() {
            return THB;
        }

        public void setTHB(Double THB) {
            this.THB = THB;
        }

        public Double getCHF() {
            return CHF;
        }

        public void setCHF(Double CHF) {
            this.CHF = CHF;
        }

        public Double getSGD() {
            return SGD;
        }

        public void setSGD(Double SGD) {
            this.SGD = SGD;
        }

        public Double getPLN() {
            return PLN;
        }

        public void setPLN(Double PLN) {
            this.PLN = PLN;
        }

        public Double getBGN() {
            return BGN;
        }

        public void setBGN(Double BGN) {
            this.BGN = BGN;
        }

        public Double getTRY() {
            return TRY;
        }

        public void setTRY(Double TRY) {
            this.TRY = TRY;
        }

        public Double getCNY() {
            return CNY;
        }

        public void setCNY(Double CNY) {
            this.CNY = CNY;
        }

        public Double getNOK() {
            return NOK;
        }

        public void setNOK(Double NOK) {
            this.NOK = NOK;
        }

        public Double getNZD() {
            return NZD;
        }

        public void setNZD(Double NZD) {
            this.NZD = NZD;
        }

        public Double getZAR() {
            return ZAR;
        }

        public void setZAR(Double ZAR) {
            this.ZAR = ZAR;
        }

        public Double getUSD() {
            return USD;
        }

        public void setUSD(Double USD) {
            this.USD = USD;
        }

        public Double getMXN() {
            return MXN;
        }

        public void setMXN(Double MXN) {
            this.MXN = MXN;
        }

        public Double getILS() {
            return ILS;
        }

        public void setILS(Double ILS) {
            this.ILS = ILS;
        }

        public Double getGBP() {
            return GBP;
        }

        public void setGBP(Double GBP) {
            this.GBP = GBP;
        }

        public Double getKRW() {
            return KRW;
        }

        public void setKRW(Double KRW) {
            this.KRW = KRW;
        }

        public Double getMYR() {
            return MYR;
        }

        public void setMYR(Double MYR) {
            this.MYR = MYR;
        }
    }
}

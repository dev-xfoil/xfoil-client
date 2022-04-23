package dev.xfoil.models;

public class BillInfo  {
    private Long iPaperid;
    private String acSn;
    private Long iPaperCode;
    private Long denomination;
    private Integer cCashSide;
    private Integer cCashDir;
    private Integer cSortResult;
    private Integer cCashExit;
    private Integer cRejectCode;
    //   private Charset acSNoImg;
    private String timestamp;
    private Long cBnSoilLevel;
    private Long cBnQualityResult;
    private Long iImageRejectCode;
    private Long iSortRejectCode;

    public BillInfo() {
    }

    public BillInfo(Long iPaperid, String acSn, Long iPaperCode, Long denomination,
                    Integer cCashSide, Integer cCashDir, Integer cSortResult,
                    Integer cCashExit, Integer cRejectCode,
                    Long cBnSoilLevel, Long cBnQualityResult, Long iImageRejectCode,
                    Long iSortRejectCode, String timestamp) {

        this.iPaperid = iPaperid;
        this.acSn = acSn;
        this.iPaperCode = iPaperCode;
        this.denomination = denomination;
        this.cCashSide = cCashSide;
        this.cCashDir = cCashDir;
        this.cSortResult = cSortResult;
        this.cCashExit = cCashExit;
        this.cRejectCode = cRejectCode;
        this.cBnSoilLevel = cBnSoilLevel;
        this.cBnQualityResult = cBnQualityResult;
        this.iImageRejectCode = iImageRejectCode;
        this.iSortRejectCode = iSortRejectCode;
        this.timestamp = timestamp;
        //this.acSNoImg = acSNoImg;
        // Charset acSNoImg,

    }

    public Long getiPaperid() {
        return iPaperid;
    }

    public void setiPaperid(Long iPaperid) {
        this.iPaperid = iPaperid;
    }

    public String getAcSn() {
        return acSn;
    }

    public void setAcSn(String acSn) {
        this.acSn = acSn;
    }

    public Long getiPaperCode() {
        return iPaperCode;
    }

    public void setiPaperCode(Long iPaperCode) {
        this.iPaperCode = iPaperCode;
    }

    public Long getDenomination() {
        return denomination;
    }

    public void setDenomination(Long denomination) {
        this.denomination = denomination;
    }

    public Integer getcCashSide() {
        return cCashSide;
    }

    public void setcCashSide(Integer cCashSide) {
        this.cCashSide = cCashSide;
    }

    public Integer getcCashDir() {
        return cCashDir;
    }

    public void setcCashDir(Integer cCashDir) {
        this.cCashDir = cCashDir;
    }

    public Integer getcSortResult() {
        return cSortResult;
    }

    public void setcSortResult(Integer cSortResult) {
        this.cSortResult = cSortResult;
    }

    public Integer getcCashExit() {
        return cCashExit;
    }

    public void setcCashExit(Integer cCashExit) {
        this.cCashExit = cCashExit;
    }

    public Integer getcRejectCode() {
        return cRejectCode;
    }

    public void setcRejectCode(Integer cRejectCode) {
        this.cRejectCode = cRejectCode;
    }


    public Long getcBnSoilLevel() {
        return cBnSoilLevel;
    }

    public void setcBnSoilLevel(Long cBnSoilLevel) {
        this.cBnSoilLevel = cBnSoilLevel;
    }

    public Long getcBnQualityResult() {
        return cBnQualityResult;
    }

    public void setcBnQualityResult(Long cBnQualityResult) {
        this.cBnQualityResult = cBnQualityResult;
    }

    public Long getiImageRejectCode() {
        return iImageRejectCode;
    }

    public void setiImageRejectCode(Long iImageRejectCode) {
        this.iImageRejectCode = iImageRejectCode;
    }

    public Long getiSortRejectCode() {
        return iSortRejectCode;
    }

    public void setiSortRejectCode(Long iSortRejectCode) {
        this.iSortRejectCode = iSortRejectCode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "{" +
                "iPaperid=" + iPaperid +
                ", acSn='" + acSn + '\'' +
                ", iPaperCode=" + iPaperCode +
                ", denomination=" + denomination +
                ", cCashSide=" + cCashSide +
                ", cCashDir=" + cCashDir +
                ", cSortResult=" + cSortResult +
                ", cCashExit=" + cCashExit +
                ", cRejectCode=" + cRejectCode +
                ", timestamp='" + timestamp + '\'' +
                ", cBnSoilLevel=" + cBnSoilLevel +
                ", cBnQualityResult=" + cBnQualityResult +
                ", iImageRejectCode=" + iImageRejectCode +
                ", iSortRejectCode=" + iSortRejectCode +
                '}';
    }
}

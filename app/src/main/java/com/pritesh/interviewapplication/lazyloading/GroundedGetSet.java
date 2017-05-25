package com.pritesh.interviewapplication.lazyloading;

import android.os.Parcel;
import android.os.Parcelable;

import com.pritesh.interviewapplication.utils.Util;

public class GroundedGetSet implements Parcelable
{
    public static final Creator CREATOR = new Creator() {
        @Override
        public GroundedGetSet createFromParcel(Parcel in)
        {
            return new GroundedGetSet(in);
        }

        @Override
        public Object[] newArray(int size)
        {
            return new GroundedGetSet[size];
        }
    };
    String carYear = "";
    String carMake = "";
    String carModel = "";
    String carName = "";
    String carSeries = "";
    String vinNo = "";
    String odaNo = "";
    String adminfee = "";
    String payOff = "";
    String residual = "";
    String vehicleGrade = "";
    String buyNowPrice = "";
    String goodThruDate = "";
    boolean isPaymentGuarantee;
    String exclusivePeriodLeft = "";
    String[] progName, progCode;
    String accountType = "";
    String storeName = "";
    String invStatusInAuction = "";
    String outstandingPymnt = "";
    String imageUrl = "";
    String inventoryId = "";
    String endDate = "";
    String saleEventItemId = "";
    String saleEventId = "";
    String delFacStatus = "";
    String delDescCode = "";
    String pricingStats = "";
    String marketBase = "";
    String carColor = "";
    String promoCode = "";
    String remPayment = "";
    String waivedPayments = "";
    boolean passedVehicle;
    boolean conditionReportAvailable;
    boolean paymentWaived;
    boolean displayGuaranteePaymentsOnly;
    boolean isMBPApproved;
    boolean noteAvailable;
    String isSaleEventActive = "";
    boolean isActive;
    boolean isPilotEnabled;
    boolean renderMarketAmount;

    //Dealer Suspension
    boolean dealerSuspendedGrndPurchase;
    boolean dealerSuspendedOnlinePurchase;
    boolean dealerSuspendedFinalDlrElx;

    public boolean isDealerSuspendedGrndPurchase()
    {
        return dealerSuspendedGrndPurchase;
    }

    public void setDealerSuspendedGrndPurchase(boolean dealerSuspendedGrndPurchase)
    {
        this.dealerSuspendedGrndPurchase = dealerSuspendedGrndPurchase;
    }

    public boolean isDealerSuspendedOnlinePurchase()
    {
        return dealerSuspendedOnlinePurchase;
    }

    public void setDealerSuspendedOnlinePurchase(boolean dealerSuspendedOnlinePurchase)
    {
        this.dealerSuspendedOnlinePurchase = dealerSuspendedOnlinePurchase;
    }

    public boolean isDealerSuspendedFinalDlrElx()
    {
        return dealerSuspendedFinalDlrElx;
    }

    public void setDealerSuspendedFinalDlrElx(boolean dealerSuspendedFinalDlrElx)
    {
        this.dealerSuspendedFinalDlrElx = dealerSuspendedFinalDlrElx;
    }

    public boolean isRenderMarketAmount()
    {
        return renderMarketAmount;
    }

    public void setRenderMarketAmount(boolean renderMarketAmount)
    {
        this.renderMarketAmount = renderMarketAmount;
    }

    public boolean isPilotEnabled()
    {
        return isPilotEnabled;
    }

    public void setPilotEnabled(boolean pilotEnabled)
    {
        isPilotEnabled = pilotEnabled;
    }

    public GroundedGetSet(Parcel in)
    {
        readFromParcel(in);
    }

    public GroundedGetSet()
    {
    }

    public void readFromParcel(Parcel in)
    {
        this.carName = in.readString();
        this.vinNo = in.readString();
        this.adminfee = in.readString();
        this.odaNo = in.readString();
        this.payOff = in.readString();
        this.residual = in.readString();
        this.vehicleGrade = in.readString();
        this.buyNowPrice = in.readString();
        this.promoCode = in.readString();
        this.progCode = in.createStringArray();
        this.progName = in.createStringArray();
        this.accountType = in.readString();
        this.storeName = in.readString();
        this.remPayment = in.readString();
        this.waivedPayments = in.readString();
        this.outstandingPymnt = in.readString();
        this.delFacStatus = in.readString();
        this.delDescCode = in.readString();
        this.pricingStats = in.readString();
        this.marketBase = in.readString();
        this.inventoryId = in.readString();
        this.saleEventItemId = in.readString();
        this.saleEventId = in.readString();
        this.endDate = in.readString();
        this.imageUrl = in.readString();
        this.invStatusInAuction = in.readString();
        this.isSaleEventActive = in.readString();
        this.isActive = in.readByte() != 0;
        this.conditionReportAvailable = in.readByte() != 0;
        this.noteAvailable = in.readByte() != 0;
        this.isMBPApproved = in.readByte() != 0;
        this.displayGuaranteePaymentsOnly = in.readByte() != 0;
        this.paymentWaived = in.readByte() != 0;
        this.goodThruDate = in.readString();
        this.isPaymentGuarantee = in.readByte() != 0;
        this.passedVehicle = in.readByte() != 0;
        this.isPilotEnabled = in.readByte() != 0;
        this.renderMarketAmount = in.readByte() != 0;
        this.dealerSuspendedGrndPurchase = in.readByte() != 0;
        this.dealerSuspendedOnlinePurchase = in.readByte() != 0;
        this.dealerSuspendedFinalDlrElx = in.readByte() != 0;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof GroundedGetSet)
            return ((GroundedGetSet)obj).getVinNo().equals(this.getVinNo());
        return false;
    }

    public String getVinNo()
    {
        return vinNo;
    }

    public void setVinNo(String vinNo)
    {
        this.vinNo = vinNo;
    }

    public String getWaivedPayments()
    {
        return waivedPayments;
    }

    public void setwaivedPayments(String waivedPayments)
    {
        this.waivedPayments = waivedPayments;
    }

    public String getStoreName()
    {
        return storeName;
    }

    public void setStoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public String getAccountType()
    {
        return accountType;
    }

    public void setAccountType(String accountType)
    {
        this.accountType = accountType;
    }

    public String[] getProgName()
    {
        return progName;
    }

    public void setProgName(String[] progName)
    {
        this.progName = progName;
    }

    public String[] getProgCode()
    {
        return progCode;
    }

    public void setProgCode(String[] progCode)
    {
        this.progCode = progCode;
    }

    public String getPromoCode()
    {
        return promoCode;
    }

    public void setPromoCode(String promoCode)
    {
        this.promoCode = promoCode;
    }

    public String getExclusivePeriodLeft()
    {
        return exclusivePeriodLeft;
    }

    public void setExclusivePeriodLeft(String exclusivePeriodLeft)
    {
        this.exclusivePeriodLeft = exclusivePeriodLeft;
    }

    public String getAdminFee()
    {
        return adminfee;
    }

    public void setAdminFee(String adminfee)
    {
        this.adminfee = adminfee;
    }

    public String getGoodThruDate()
    {
        return goodThruDate;
    }

    public void setGoodThruDate(String goodThruDate)
    {
        this.goodThruDate = goodThruDate;
    }

    public boolean isPassedVehicle()
    {
        return passedVehicle;
    }

    public void setPassedVehicle(boolean passedVehicle)
    {
        this.passedVehicle = passedVehicle;
    }

    public String getBuyNowPrice()
    {
        return buyNowPrice;
    }

    public void setBuyNowPrice(String buyNowPrice)
    {
        this.buyNowPrice = buyNowPrice;
    }

    public String getVehicleGrade()
    {
        return vehicleGrade;
    }

    public void setVehicleGrade(String vehicleGrade)
    {
        this.vehicleGrade = vehicleGrade;
    }

    public boolean isPaymentWaived()
    {
        return paymentWaived;
    }

    public void setPaymentWaived(boolean paymentWaived)
    {
        this.paymentWaived = paymentWaived;
    }

    public boolean isDisplayGuaranteePaymentsOnly()
    {
        return displayGuaranteePaymentsOnly;
    }

    public void setDisplayGuaranteePaymentsOnly(boolean displayGuaranteePaymentsOnly)
    {
        this.displayGuaranteePaymentsOnly = displayGuaranteePaymentsOnly;
    }

    public boolean isMBPApproved()
    {
        return isMBPApproved;
    }

    public void setMBPApproved(boolean isMBPApproved)
    {
        this.isMBPApproved = isMBPApproved;
    }

    public boolean isNoteAvailable()
    {
        return noteAvailable;
    }

    public void setNoteAvailable(boolean noteAvailable)
    {
        this.noteAvailable = noteAvailable;
    }

    public String getInvStatusInAuction()
    {
        return invStatusInAuction;
    }

    public void setInvStatusInAuction(String invStatusInAuction)
    {
        this.invStatusInAuction = invStatusInAuction;
    }

    public String getOutstandingPymnt()
    {
        return outstandingPymnt;
    }

    public void setOutstandingPymnt(String outstandingPymnt)
    {
        this.outstandingPymnt = outstandingPymnt;
    }

    public boolean isPaymentGuarantee()
    {
        return isPaymentGuarantee;
    }

    public void setPaymentGuarantee(boolean isPaymentGuarantee)
    {
        this.isPaymentGuarantee = isPaymentGuarantee;
    }

    public String getIsSaleEventActive()
    {
        return isSaleEventActive;
    }

    public void setIsSaleEventActive(String isSaleEventActive)
    {
        this.isSaleEventActive = isSaleEventActive;
    }

    public String getCarColor()
    {
        return carColor;
    }

    public void setCarColor(String carColor)
    {
        this.carColor = carColor;
    }

    public boolean isConditionReportAvailable()
    {
        return conditionReportAvailable;
    }

    public void setConditionReportAvailable(boolean conditionReportAvailable)
    {
        this.conditionReportAvailable = conditionReportAvailable;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    public String getCarYear()
    {
        return carYear;
    }

    public void setCarYear(String carYear)
    {
        this.carYear = carYear;
    }

    public String getCarMake()
    {
        return carMake;
    }

    public void setCarMake(String carMake)
    {
        this.carMake = carMake;
    }

    public String getCarModel()
    {
        return carModel;
    }

    public void setCarModel(String carModel)
    {
        this.carModel = carModel;
    }

    public String getcarSeries()
    {
        return carSeries;
    }

    public void setcarSeries(String carSeries)
    {
        this.carSeries = carSeries;
    }

    public String getSaleEventItemId()
    {
        return saleEventItemId;
    }

    public void setSaleEventItemId(String saleEventItemId)
    {
        this.saleEventItemId = saleEventItemId;
    }

    public String getSaleEventId()
    {
        return saleEventId;
    }

    public void setSaleEventId(String saleEventId)
    {
        this.saleEventId = saleEventId;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public String getInventoryId()
    {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId)
    {
        this.inventoryId = inventoryId;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.carName);
        dest.writeString(this.adminfee);
        dest.writeString(this.vinNo);
        dest.writeString(this.odaNo);
        dest.writeString(this.payOff);
        dest.writeString(this.residual);
        dest.writeString(this.vehicleGrade);
        dest.writeString(this.buyNowPrice);
        dest.writeString(this.promoCode);
        dest.writeStringArray(this.progCode);
        dest.writeStringArray(this.progName);
        dest.writeString(this.accountType);
        dest.writeString(this.storeName);
        dest.writeString(this.remPayment);
        dest.writeString(this.waivedPayments);
        dest.writeString(this.outstandingPymnt);
        dest.writeString(this.delFacStatus);
        dest.writeString(this.delDescCode);
        dest.writeString(this.pricingStats);
        dest.writeString(this.marketBase);
        dest.writeString(this.inventoryId);
        dest.writeString(this.saleEventItemId);
        dest.writeString(this.saleEventId);
        dest.writeString(this.endDate);
        dest.writeString(this.imageUrl);
        dest.writeString(this.invStatusInAuction);
        dest.writeString(this.isSaleEventActive);
        dest.writeByte((byte)(this.isActive ? 1 : 0));
        dest.writeByte((byte)(this.conditionReportAvailable ? 1 : 0));
        dest.writeByte((byte)(this.noteAvailable ? 1 : 0));
        dest.writeByte((byte)(this.isMBPApproved ? 1 : 0));
        dest.writeByte((byte)(this.displayGuaranteePaymentsOnly ? 1 : 0));
        dest.writeByte((byte)(this.paymentWaived ? 1 : 0));
        dest.writeString(this.goodThruDate);
        dest.writeByte((byte)(this.isPaymentGuarantee ? 1 : 0));
        dest.writeByte((byte)(this.passedVehicle ? 1 : 0));
        dest.writeByte((byte)(this.isPilotEnabled ? 1 : 0));
        dest.writeByte((byte)(this.renderMarketAmount ? 1 : 0));
        dest.writeByte((byte)(this.dealerSuspendedGrndPurchase ? 1 : 0));
        dest.writeByte((byte)(this.dealerSuspendedOnlinePurchase ? 1 : 0));
        dest.writeByte((byte)(this.dealerSuspendedFinalDlrElx ? 1 : 0));
    }

    public String getMarketBase()
    {
        return marketBase;
    }

    public void setMarketBase(String marketBase)
    {
        this.marketBase = marketBase;
    }

    public String getDelFacStatus()
    {
        return delFacStatus;
    }

    public void setDelFacStatus(String delFacStatus)
    {
        this.delFacStatus = delFacStatus;
    }

    public String getDelDescCode()
    {
        return delDescCode;
    }

    public void setDelDescCode(String delDescCode)
    {
        this.delDescCode = delDescCode;
    }

    public String getPricingStats()
    {
        return pricingStats;
    }

    public void setPricingStats(String pricingStats)
    {
        this.pricingStats = pricingStats;
    }

    public String getCarName()
    {
        return carName;
    }

    public void setCarName(String carName)
    {
        this.carName = carName;
    }

    public String getOdaNo()
    {
        return odaNo;
    }

    public void setOdaNo(String odaNo)
    {
        this.odaNo = odaNo;
    }

    public String getPayOff()
    {
        return payOff;
    }

    public void setPayOff(String payOff)
    {
        this.payOff = payOff;
    }

    public String getResidual()
    {
        return residual;
    }

    public void setResidual(String residual)
    {
        this.residual = residual;
    }

    public String getRemPayment()
    {
        return remPayment;
    }

    public void setRemPayment(String remPayment)
    {
        this.remPayment = remPayment;
    }

    public String getImageUrl()
    {
        return Util.replaceHttps(imageUrl);
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }
}
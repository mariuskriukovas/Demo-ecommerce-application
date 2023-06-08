package io.marius.demo.ecommerce.elasticindexerservice.elastic.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "public_phone_index")
public class PhoneIndex {
  @Id
  @Field(name = "uid")
  private String uid;

  private String brand;
  private String networkTechnology;
  private String _2gBands;
  private String _4gBands;
  private Boolean gprs;
  private Boolean edge;
  private String announced;
  private String status;
  private String dimentions;
  private Float weightG;
  private Float weightOz;
  private String sim;
  private String displayType;
  private String displayResolution;
  private String displaySize;
  private String os;
  private String cpu;
  private String chipset;
  private String gpu;
  private String memoryCard;
  private String internalMemory;
  private String ram;
  private String primaryCamera;
  private String secondaryCamera;
  private String loudSpeaker;
  private Boolean audioJack;
  private String wlan;
  private String bluetooth;
  private String gps;
  private String nfc;
  private String radio;
  private String usb;
  private String sensors;
  private String battery;
  private String colors;
  private String approxPriceEur;

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getNetworkTechnology() {
    return networkTechnology;
  }

  public void setNetworkTechnology(String networkTechnology) {
    this.networkTechnology = networkTechnology;
  }

  public String get_2gBands() {
    return _2gBands;
  }

  public void set_2gBands(String _2gBands) {
    this._2gBands = _2gBands;
  }

  public String get_4gBands() {
    return _4gBands;
  }

  public void set_4gBands(String _4gBands) {
    this._4gBands = _4gBands;
  }

  public Boolean getGprs() {
    return gprs;
  }

  public void setGprs(Boolean gprs) {
    this.gprs = gprs;
  }

  public Boolean getEdge() {
    return edge;
  }

  public void setEdge(Boolean edge) {
    this.edge = edge;
  }

  public String getAnnounced() {
    return announced;
  }

  public void setAnnounced(String announced) {
    this.announced = announced;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDimentions() {
    return dimentions;
  }

  public void setDimentions(String dimentions) {
    this.dimentions = dimentions;
  }

  public Float getWeightG() {
    return weightG;
  }

  public void setWeightG(Float weightG) {
    this.weightG = weightG;
  }

  public Float getWeightOz() {
    return weightOz;
  }

  public void setWeightOz(Float weightOz) {
    this.weightOz = weightOz;
  }

  public String getSim() {
    return sim;
  }

  public void setSim(String sim) {
    this.sim = sim;
  }

  public String getDisplayType() {
    return displayType;
  }

  public void setDisplayType(String displayType) {
    this.displayType = displayType;
  }

  public String getDisplayResolution() {
    return displayResolution;
  }

  public void setDisplayResolution(String displayResolution) {
    this.displayResolution = displayResolution;
  }

  public String getDisplaySize() {
    return displaySize;
  }

  public void setDisplaySize(String displaySize) {
    this.displaySize = displaySize;
  }

  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }

  public String getCpu() {
    return cpu;
  }

  public void setCpu(String cpu) {
    this.cpu = cpu;
  }

  public String getChipset() {
    return chipset;
  }

  public void setChipset(String chipset) {
    this.chipset = chipset;
  }

  public String getGpu() {
    return gpu;
  }

  public void setGpu(String gpu) {
    this.gpu = gpu;
  }

  public String getMemoryCard() {
    return memoryCard;
  }

  public void setMemoryCard(String memoryCard) {
    this.memoryCard = memoryCard;
  }

  public String getInternalMemory() {
    return internalMemory;
  }

  public void setInternalMemory(String internalMemory) {
    this.internalMemory = internalMemory;
  }

  public String getRam() {
    return ram;
  }

  public void setRam(String ram) {
    this.ram = ram;
  }

  public String getPrimaryCamera() {
    return primaryCamera;
  }

  public void setPrimaryCamera(String primaryCamera) {
    this.primaryCamera = primaryCamera;
  }

  public String getSecondaryCamera() {
    return secondaryCamera;
  }

  public void setSecondaryCamera(String secondaryCamera) {
    this.secondaryCamera = secondaryCamera;
  }

  public String getLoudSpeaker() {
    return loudSpeaker;
  }

  public void setLoudSpeaker(String loudSpeaker) {
    this.loudSpeaker = loudSpeaker;
  }

  public Boolean getAudioJack() {
    return audioJack;
  }

  public void setAudioJack(Boolean audioJack) {
    this.audioJack = audioJack;
  }

  public String getWlan() {
    return wlan;
  }

  public void setWlan(String wlan) {
    this.wlan = wlan;
  }

  public String getBluetooth() {
    return bluetooth;
  }

  public void setBluetooth(String bluetooth) {
    this.bluetooth = bluetooth;
  }

  public String getGps() {
    return gps;
  }

  public void setGps(String gps) {
    this.gps = gps;
  }

  public String getNfc() {
    return nfc;
  }

  public void setNfc(String nfc) {
    this.nfc = nfc;
  }

  public String getRadio() {
    return radio;
  }

  public void setRadio(String radio) {
    this.radio = radio;
  }

  public String getUsb() {
    return usb;
  }

  public void setUsb(String usb) {
    this.usb = usb;
  }

  public String getSensors() {
    return sensors;
  }

  public void setSensors(String sensors) {
    this.sensors = sensors;
  }

  public String getBattery() {
    return battery;
  }

  public void setBattery(String battery) {
    this.battery = battery;
  }

  public String getColors() {
    return colors;
  }

  public void setColors(String colors) {
    this.colors = colors;
  }

  public String getApproxPriceEur() {
    return approxPriceEur;
  }

  public void setApproxPriceEur(String approxPriceEur) {
    this.approxPriceEur = approxPriceEur;
  }
}

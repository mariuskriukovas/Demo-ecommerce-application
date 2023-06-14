package io.marius.demo.ecommerce.elasticindexerservice.sheet;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;

@ExcelSheet("phone_dataset")
public class PhoneDataRow {
  @ExcelCellName("brand")
  private String brand;

  @ExcelCellName("model")
  private String model;

  @ExcelCellName("img_url")
  private String imgUrl;

  @ExcelCellName("network_technology")
  private String networkTechnology;

  @ExcelCellName("2G_bands")
  private String _2gBands;

  @ExcelCellName("4G_bands")
  private String _4gBands;

  @ExcelCellName("GPRS")
  private String gprs;

  @ExcelCellName("EDGE")
  private String edge;

  @ExcelCellName("announced")
  private String announced;

  @ExcelCellName("status")
  private String status;

  @ExcelCellName("dimentions")
  private String dimentions;

  @ExcelCellName("weight_g")
  private Float weightG;

  @ExcelCellName("weight_oz")
  private Float weightOz;

  @ExcelCellName("SIM")
  private String sim;

  @ExcelCellName("display_type")
  private String displayType;

  @ExcelCellName("display_resolution")
  private String displayResolution;

  @ExcelCellName("display_size")
  private String displaySize;

  @ExcelCellName("OS")
  private String os;

  @ExcelCellName("CPU")
  private String cpu;

  @ExcelCellName("Chipset")
  private String chipset;

  @ExcelCellName("GPU")
  private String gpu;

  @ExcelCellName("memory_card")
  private String memoryCard;

  @ExcelCellName("internal_memory")
  private String internalMemory;

  @ExcelCellName("RAM")
  private String ram;

  @ExcelCellName("primary_camera")
  private String primaryCamera;

  @ExcelCellName("secondary_camera")
  private String secondaryCamera;

  @ExcelCellName("loud_speaker")
  private String loudSpeaker;

  @ExcelCellName("audio_jack")
  private String audioJack;

  @ExcelCellName("WLAN")
  private String wlan;

  @ExcelCellName("bluetooth")
  private String bluetooth;

  @ExcelCellName("GPS")
  private String gps;

  @ExcelCellName("NFC")
  private String nfc;

  @ExcelCellName("radio")
  private String radio;

  @ExcelCellName("USB")
  private String usb;

  @ExcelCellName("sensors")
  private String sensors;

  @ExcelCellName("battery")
  private String battery;

  @ExcelCellName("colors")
  private String colors;

  @ExcelCellName("approx_price_EUR")
  private String approxPriceEur;

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

  public String getGprs() {
    return gprs;
  }

  public void setGprs(String gprs) {
    this.gprs = gprs;
  }

  public String getEdge() {
    return edge;
  }

  public void setEdge(String edge) {
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

  public String getAudioJack() {
    return audioJack;
  }

  public void setAudioJack(String audioJack) {
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

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }
}

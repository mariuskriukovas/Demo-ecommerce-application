package io.marius.demo.ecommerce.elasticindexerservice.elastic.document;

import io.marius.demo.ecommerce.elasticindexerservice.sheet.PhoneDataRow;
import io.marius.demo.ecommerce.inventory.api.entity.*;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "public_product_index")
public class PhoneProduct implements BaseProduct {
  private final List<ProductProperty> properties;
  private final String name;
  private final Double price;
  private final String description;
  private final List<ProductFile> files;

  @Id
  @Field(name = "uid")
  private String uid;

  public PhoneProduct(PhoneDataRow phoneDataRow) {
    name = phoneDataRow.getBrand() + " " + phoneDataRow.getModel();
    price = toDouble(phoneDataRow.getApproxPriceEur());
    description = phoneDataRow.getStatus();

    properties =
        List.of(
            new ProductProperty("Network Technology", phoneDataRow.getNetworkTechnology()),
            new ProductProperty("2G Bands", phoneDataRow.get_2gBands()),
            new ProductProperty("4G Bands", phoneDataRow.get_4gBands()),
            new ProductProperty("GPRS", phoneDataRow.getGprs()),
            new ProductProperty("EDGE", phoneDataRow.getEdge()),
            new ProductProperty("Announced", phoneDataRow.getAnnounced()),
            new ProductProperty("Dimentions", phoneDataRow.getDimentions()),
            new ProductProperty("Weight G.", toString(phoneDataRow.getWeightG())),
            new ProductProperty("Weight Oz.", toString(phoneDataRow.getWeightOz())),
            new ProductProperty("SIM", phoneDataRow.getSim()),
            new ProductProperty("Display Type", phoneDataRow.getDisplayType()),
            new ProductProperty("Display Resolution", phoneDataRow.getDisplayResolution()),
            new ProductProperty("Display Size", phoneDataRow.getDisplaySize()),
            new ProductProperty("OS", phoneDataRow.getOs()),
            new ProductProperty("CPU", phoneDataRow.getCpu()),
            new ProductProperty("Chipset", phoneDataRow.getChipset()),
            new ProductProperty("GPU", phoneDataRow.getGpu()),
            new ProductProperty("Memory Card", phoneDataRow.getMemoryCard()),
            new ProductProperty("Internal Memory", phoneDataRow.getInternalMemory()),
            new ProductProperty("RAM", phoneDataRow.getRam()),
            new ProductProperty("Primary Camera", phoneDataRow.getPrimaryCamera()),
            new ProductProperty("Secondary Camera", phoneDataRow.getSecondaryCamera()),
            new ProductProperty("Loud Speaker", phoneDataRow.getLoudSpeaker()),
            new ProductProperty("Audio Jack", phoneDataRow.getAudioJack()),
            new ProductProperty("WLAN", phoneDataRow.getWlan()),
            new ProductProperty("Bluetooth", phoneDataRow.getBluetooth()),
            new ProductProperty("GPS", phoneDataRow.getGps()),
            new ProductProperty("NFC", phoneDataRow.getNfc()),
            new ProductProperty("Radio", phoneDataRow.getRadio()),
            new ProductProperty("USB", phoneDataRow.getUsb()),
            new ProductProperty("Sensors", phoneDataRow.getSensors()),
            new ProductProperty("Battery", phoneDataRow.getBattery()),
            new ProductProperty("Colors", phoneDataRow.getColors()));

    String fileName = getSubstringFromLastCharSeparator(phoneDataRow.getImgUrl(), '/');
    String extension = getSubstringFromLastCharSeparator(phoneDataRow.getImgUrl(), '.');
    files =
        List.of(
            new ProductFile(
                new FileMetadata(fileName, phoneDataRow.getImgUrl(), extension, fileName)));
  }

  private Double toDouble(String number) {
    if (number == null || number.isEmpty()) return null;

    try {
      return Double.parseDouble(number);
    } catch (Exception ignored) {
      return null;
    }
  }

  private String toString(Object number) {
    if (number == null) {
      return null;
    }
    return number.toString();
  }

  private String getSubstringFromLastCharSeparator(String url, char separator) {
    if (url == null || url.isEmpty()) return null;
    try {
      return url.substring(url.lastIndexOf(separator) + 1);
    } catch (Exception ignored) {
      return null;
    }
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Double getPrice() {
    return price;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public BaseProductCategory getProductCategory() {
    return null;
  }

  @Override
  public List<? extends BaseProductProperty> getProperties() {
    return properties;
  }

  @Override
  public List<? extends BaseProductFile> getProductFiles() {
    return files;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }
}

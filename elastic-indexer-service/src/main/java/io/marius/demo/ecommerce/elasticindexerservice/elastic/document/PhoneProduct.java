package io.marius.demo.ecommerce.elasticindexerservice.elastic.document;

import io.marius.demo.ecommerce.elasticindexerservice.sheet.PhoneDataRow;
import io.marius.demo.ecommerce.inventory.api.entity.*;
import java.util.List;
import java.util.UUID;
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
    uid = getRandomUid();
    name = phoneDataRow.getBrand() + " " + phoneDataRow.getModel();
    price = toDouble(phoneDataRow.getApproxPriceEur());
    description = phoneDataRow.getStatus();

    properties =
        List.of(
            new ProductProperty(
                getRandomUid(), "Network Technology", phoneDataRow.getNetworkTechnology()),
            new ProductProperty(getRandomUid(), "2G Bands", phoneDataRow.get_2gBands()),
            new ProductProperty(getRandomUid(), "4G Bands", phoneDataRow.get_4gBands()),
            new ProductProperty(getRandomUid(), "GPRS", phoneDataRow.getGprs()),
            new ProductProperty(getRandomUid(), "EDGE", phoneDataRow.getEdge()),
            new ProductProperty(getRandomUid(), "Announced", phoneDataRow.getAnnounced()),
            new ProductProperty(getRandomUid(), "Dimentions", phoneDataRow.getDimentions()),
            new ProductProperty(getRandomUid(), "Weight G.", toString(phoneDataRow.getWeightG())),
            new ProductProperty(getRandomUid(), "Weight Oz.", toString(phoneDataRow.getWeightOz())),
            new ProductProperty(getRandomUid(), "SIM", phoneDataRow.getSim()),
            new ProductProperty(getRandomUid(), "Display Type", phoneDataRow.getDisplayType()),
            new ProductProperty(
                getRandomUid(), "Display Resolution", phoneDataRow.getDisplayResolution()),
            new ProductProperty(getRandomUid(), "Display Size", phoneDataRow.getDisplaySize()),
            new ProductProperty(getRandomUid(), "OS", phoneDataRow.getOs()),
            new ProductProperty(getRandomUid(), "CPU", phoneDataRow.getCpu()),
            new ProductProperty(getRandomUid(), "Chipset", phoneDataRow.getChipset()),
            new ProductProperty(getRandomUid(), "GPU", phoneDataRow.getGpu()),
            new ProductProperty(getRandomUid(), "Memory Card", phoneDataRow.getMemoryCard()),
            new ProductProperty(
                getRandomUid(), "Internal Memory", phoneDataRow.getInternalMemory()),
            new ProductProperty(getRandomUid(), "RAM", phoneDataRow.getRam()),
            new ProductProperty(getRandomUid(), "Primary Camera", phoneDataRow.getPrimaryCamera()),
            new ProductProperty(
                getRandomUid(), "Secondary Camera", phoneDataRow.getSecondaryCamera()),
            new ProductProperty(getRandomUid(), "Loud Speaker", phoneDataRow.getLoudSpeaker()),
            new ProductProperty(getRandomUid(), "Audio Jack", phoneDataRow.getAudioJack()),
            new ProductProperty(getRandomUid(), "WLAN", phoneDataRow.getWlan()),
            new ProductProperty(getRandomUid(), "Bluetooth", phoneDataRow.getBluetooth()),
            new ProductProperty(getRandomUid(), "GPS", phoneDataRow.getGps()),
            new ProductProperty(getRandomUid(), "NFC", phoneDataRow.getNfc()),
            new ProductProperty(getRandomUid(), "Radio", phoneDataRow.getRadio()),
            new ProductProperty(getRandomUid(), "USB", phoneDataRow.getUsb()),
            new ProductProperty(getRandomUid(), "Sensors", phoneDataRow.getSensors()),
            new ProductProperty(getRandomUid(), "Battery", phoneDataRow.getBattery()),
            new ProductProperty(getRandomUid(), "Colors", phoneDataRow.getColors()));

    String fileName = getSubstringFromLastCharSeparator(phoneDataRow.getImgUrl(), '/');
    String extension = getSubstringFromLastCharSeparator(phoneDataRow.getImgUrl(), '.');
    files =
        List.of(
            new ProductFile(
                getRandomUid(),
                new FileMetadata(
                    getRandomUid(), fileName, phoneDataRow.getImgUrl(), extension, fileName)));
  }

  private String getRandomUid() {
    return UUID.randomUUID().toString();
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

  public String getName() {
    return name;
  }

  public Double getPrice() {
    return price;
  }

  public String getDescription() {
    return description;
  }

  public BaseProductCategory getProductCategory() {
    return null;
  }

  public List<? extends BaseProductProperty> getProperties() {
    return properties;
  }

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

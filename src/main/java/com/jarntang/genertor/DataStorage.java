package com.jarntang.genertor;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

/**
 * 输入信息
 *
 * @author Qinmu
 * @since 2016/10/24 17:09
 */
public class DataStorage {

  @Data
  @Builder
  public static class Configuration implements Serializable {

    public String port;
    public String address;
    public String userName;
    public String password;
    public String database;
    public String outputDir;

    public String getDbProtocolAddress() {
      return "jdbc:mysql://" + address + ":" + port + "/" + database;
    }
  }

}

package main.server.CodeFormat;

public class PoorlyFormatted {

  private Integer thisIsBadCode = 100;

  PoorlyFormatted(Integer badCode) {
    this.thisIsBadCode = badCode;
  }

  public Integer badLoop() {
    Integer count = 0;

    for (int i = 0; i < thisIsBadCode; i++) {
      count--;
    }

    return count;
  }

  public Integer getThisIsBadCode() {

    return this.thisIsBadCode;
  }

  public void setThisIsBadCode(Integer worseCode) {

    this.thisIsBadCode = worseCode;
  }
}

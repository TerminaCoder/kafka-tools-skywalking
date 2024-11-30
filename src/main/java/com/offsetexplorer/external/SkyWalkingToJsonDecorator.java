package com.offsetexplorer.external;

import com.google.protobuf.util.JsonFormat;
import org.apache.skywalking.apm.network.language.agent.v3.SegmentObject;

import java.util.Map;

public class SkyWalkingToJsonDecorator implements ICustomMessageDecorator2 {
  JsonFormat.Printer printer = JsonFormat.printer();

  @Override
  public String getDisplayName() { return "skyWalkingToJson"; }

  @Override
  public String decorate(String zookeeperHost, String brokerHost, String topic, long partitionId, long offset, byte[] msg, Map<String, byte[]> headers, Map<String, String> reserved) {
    try {
      return printer.includingDefaultValueFields().print(SegmentObject.parseFrom(msg));
    } catch(Throwable t) {
      return "Error: " + t.getMessage();
    }
  }
}

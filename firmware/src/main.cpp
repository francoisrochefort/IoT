

#include <Arduino.h>
#include <ArduinoJson.h>

void setup() {
  Serial.begin(115200);
  while(!Serial);
}

void loop() {

  StaticJsonDocument<1024> doc;
  doc["command"] = "outputDebugString";
  doc["parameters"][0]["debugString"] = "There is no spoon!";

  String json;
  serializeJsonPretty(doc, json);
  Serial.print(json);
  delay(100);
}
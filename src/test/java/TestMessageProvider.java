/*
 *
 *  * Copyright 2019 InfAI (CC SES)
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

import org.infai.seits.sepl.operators.Builder;
import org.infai.seits.sepl.operators.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestMessageProvider {

    public static List<Message> getTestMesssagesSet() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("src/test/resources/sample-data-small.json"));
        Builder builder = new Builder("1", "1");
        List<Message> messageSet = new ArrayList<>();
        JSONObject config = getConfig();
        String line;
        Message m;
        JSONObject jsonObjectRead, jsonObject;
        while ((line = br.readLine()) != null) {
            jsonObjectRead = new JSONObject(line);
            jsonObject = new JSONObject().put("device_id", "1").put("value", new JSONObject().put("reading", jsonObjectRead));
            m = new Message(builder.formatMessage(jsonObject.toString()));
            m.setConfig(config.toString());
            messageSet.add(m);
        }
        return messageSet;
    }

    public static JSONObject getConfig() {
        JSONObject config = new JSONObject().put("inputTopics",new JSONArray().put(new JSONObject().put("Name", "test")
                .put("FilterType", "DeviceId")
                .put("FilterValue", "1")
                .put("Mappings", new JSONArray()
                        .put(new JSONObject().put("Source", "value.reading.value1").put("Dest", "value1"))
                        .put(new JSONObject().put("Source", "value.reading.timestamp1").put("Dest", "timestamp1"))
                        .put(new JSONObject().put("Source", "value.reading.value2").put("Dest", "value2"))
                        .put(new JSONObject().put("Source", "value.reading.timestamp2").put("Dest", "timestamp2"))
                        .put(new JSONObject().put("Source", "value.reading.value").put("Dest", "value"))
                        .put(new JSONObject().put("Source", "value.reading.timestamp").put("Dest", "timestamp"))
                )));
        return config;
    }
}
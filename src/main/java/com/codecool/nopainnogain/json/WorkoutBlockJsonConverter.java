package com.codecool.nopainnogain.json;

import com.codecool.nopainnogain.model.WorkoutBlock;
import com.codecool.nopainnogain.model.WorkoutComponent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkoutBlockJsonConverter {

    public static String blockToJson(WorkoutBlock block){
        ObjectNode blockJson = JsonNodeFactory.instance.objectNode();

        blockJson.put("order",block.getOrder());

        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();

        for(WorkoutComponent comp: block.getComponents()){
            arrayNode.add(WorkoutComponentJsonConverter.componentToJson(comp));
        }

        blockJson.put("components",arrayNode.toString());

        return blockJson.toString();
    }

    public static WorkoutBlock jsonToBlock(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = null;
        WorkoutBlock resultBlock = new WorkoutBlock();


        try {
            map = mapper.readValue(jsonString,new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        int order =(int) map.get("order");
        List<WorkoutComponent> wcomponents = new ArrayList<>();

        try {
            List<String> components = mapper.readValue((String)map.get("components"),new TypeReference<List<String>>() {} );
            for(String comp: components){
                wcomponents.add(WorkoutComponentJsonConverter.jsonToComponent(comp));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        resultBlock.setOrder(order);
        for(WorkoutComponent comp: wcomponents){
            resultBlock.addComponent(comp);
        }

        return resultBlock;




    }

}

package persistence;

import org.json.JSONObject;

/***************************************************************************************
*    Title: JsonSerializationDemo source code
*    Author: Carter, P
*    Date: 10/18/24
*    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
*
***************************************************************************************/
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

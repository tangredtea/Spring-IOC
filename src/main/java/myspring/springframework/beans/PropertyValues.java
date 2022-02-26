package myspring.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue value){
        for (int i = 0; i < propertyValueList.size(); i++) {
            PropertyValue propertyValue = this.propertyValueList.get(i);
            if (propertyValue.getName().equals(value.getName())){
                // 覆盖原有值
                this.propertyValueList.set(i, value);
                return;
            }
        }
        this.propertyValueList.add(value);
    }

    public PropertyValue[] getPropertyValues(){
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String propertyName){
        for (PropertyValue propertyValue : this.propertyValueList) {
            if (propertyValue.getName().equals(propertyName)){
                return propertyValue;
            }
        }
        return null;
    }

}

package com.enqbs.app.pojo;

import com.enqbs.generator.pojo.Spu;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ESSyncProducts {

    private List<String> oldIds;

    private List<Spu> data;

    @Override
    public String toString() {
        return "ESSyncProducts{" +
                "oldIds=" + oldIds +
                ", data=" + data +
                '}';
    }

}

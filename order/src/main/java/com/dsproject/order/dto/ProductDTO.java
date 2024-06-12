//package com.dsproject.order.dto;
//
//import lombok.Data;
//
//@Data
//public class ProductDTO {
//    private Long id;
//    private String name;
//    private double  price;
//}

package com.dsproject.order.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private double price;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

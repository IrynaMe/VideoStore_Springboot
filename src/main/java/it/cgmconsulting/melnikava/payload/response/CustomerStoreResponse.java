package it.cgmconsulting.melnikava.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerStoreResponse {
    private String storeName;
    private long totalCustomers;

}

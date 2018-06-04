package com.codenotfound.kafka.flowable.mock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

/**
 * Created by kevin on 04/06/2018.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated

public class AssetChangeRequest{

    String user;
    String org;
    String symbol;
    BigDecimal amount;

}

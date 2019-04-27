package com.wiredbraincoffee.reward.test;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.wiredbraincoffee.product.Product;
import com.wiredbraincoffee.reward.RewardByGiftService;
import com.wiredbraincoffee.reward.RewardInformation;

public class RewardByGiftServiceTest {
    private RewardByGiftService reward = null;

    @BeforeEach
    void setUp() {
        reward = new RewardByGiftService();
        reward.setGiftProductId(4);
        reward.setNeededPoints(100);
    }

    @Test
    @DisplayName("Reward applied with enough points")
    void rewardApplied() {
        RewardInformation info = reward.applyReward(
                buildSampleOrder(10), 200
        );
        
        assertAll("Reward info errors",
                () -> assertNotNull(info),
                () -> assertEquals(2.99, info.getDiscount()),
                () -> assertEquals(100, info.getPointsRedeemed())
        );
    }

    @Test
    @DisplayName("Exception is thrown when invalid product ID is set")
    void exceptionThrownWhenInvalidProductID() {
        long productId = -1;
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            reward.setGiftProductId(productId);
        });
        assertTrue(exception.getMessage().contains(String.valueOf(productId)));
    }

    private List<Product> buildSampleOrder(int numberOfProducts) {
        List<Product> list = IntStream.range(1, numberOfProducts + 1)
                .mapToObj(i -> new Product(i, "Product " + i, 2.99))
                .collect(toList());
        return list;
    }

}
